package com.tess.things.service

import arrow.core.flatMap
import arrow.core.left
import arrow.core.orNull
import com.tess.architecture.sdk.interfaces.ApplicationStarter
import com.tess.architecture.sdk.interfaces.CoroutineHandler
import com.tess.architecture.sdk.utils.SmartLogger
import com.tess.things.interfaces.DataSubscriber
import com.tess.things.interfaces.DataUpdater
import com.tess.things.models.AqaraSensor
import com.tess.things.models.IP
import com.tess.things.models.Temperature
import com.tess.things.models.TemperatureSensor
import com.tess.things.models.aqara.AqaraMessageData
import com.tess.things.models.aqara.AqaraNetCommand
import com.tess.things.models.aqara.AqaraNetMessage
import com.tess.things.models.asHumidity
import com.tess.things.models.asPressure
import com.tess.things.models.asTemperature
import com.tess.things.models.asTemperatureSensor
import com.tess.things.network.UDPMessenger
import com.tess.things.network.sendAndReceiveMessage
import com.tess.things.utils.Retriable
import com.tess.core.network.ObjectParser
import com.tess.core.network.parseJson
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val SUPPORTED_MODEL = "weather.v1"
private const val GATEWAY_UDP_PORT = 9898
private const val GATEWAY_CMD_READ = "read"

class TemperatureSensorServiceImpl(
    private val udpMessenger: UDPMessenger,
    private val objectParser: ObjectParser,
    private val temperatureUpdater: DataUpdater<Temperature>,
    ipSubscriber: DataSubscriber<IP>,
    sensorDiscoverySubscriber: DataSubscriber<List<AqaraSensor>>
) : TemperatureSensorService, ApplicationStarter, CoroutineHandler {

    override val job = Job()

    private var currentIP: IP = IP()
    private val temperatureSensors: MutableSet<TemperatureSensor> = mutableSetOf()

    init {
        launch(Default) {
            ipSubscriber.subscribeDataUpdate().consumeEach {
                currentIP = it
            }
        }

        launch(Default) {
            sensorDiscoverySubscriber.subscribeDataUpdate().consumeEach { sensors ->
                temperatureSensors.addAll(
                    sensors.filter { it.modelCode == SUPPORTED_MODEL }
                        .map(AqaraSensor::asTemperatureSensor)
                )
                updateSensorData()
            }
        }
    }

    override fun updateSensorData() {
        temperatureSensors.forEach {
            launch {
                it.readSensor()
            }
        }

        job.invokeOnCompletion {
            SmartLogger.d("read temperature")
            SmartLogger.d(temperatureSensors.first().temperature)
        }
    }

    private suspend fun TemperatureSensor.readSensor() {
        withContext(Default) {
            Retriable {
                udpMessenger.sendAndReceiveMessage<AqaraNetCommand, AqaraNetMessage>(
                    currentIP,
                    GATEWAY_UDP_PORT,
                    AqaraNetCommand(GATEWAY_CMD_READ, id)
                )
            }.retryIfIsNotAckOf(GATEWAY_CMD_READ)
                .flatMap { message ->
                    message.dataJson?.let { objectParser.parseJson<AqaraMessageData>(it) } ?: left()
                }
                .orNull()
                ?.let {
                    temperature = it.temperature.asTemperature()
                    humidity = it.humidity.asHumidity()
                    pressure = it.pressure.asPressure()
                }
        }
    }
}