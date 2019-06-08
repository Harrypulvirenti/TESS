package com.hpdev.smartthermostat.service

import arrow.core.flatMap
import arrow.core.left
import arrow.core.orNull
import com.hpdev.architecture.sdk.interfaces.ApplicationStarter
import com.hpdev.architecture.sdk.interfaces.CoroutineHandler
import com.hpdev.architecture.sdk.utils.SmartLogger
import com.hpdev.smartthermostat.interfaces.DataSubscriber
import com.hpdev.smartthermostat.interfaces.DataUpdater
import com.hpdev.smartthermostat.models.AqaraSensor
import com.hpdev.smartthermostat.models.IP
import com.hpdev.smartthermostat.models.Temperature
import com.hpdev.smartthermostat.models.TemperatureSensor
import com.hpdev.smartthermostat.models.aqara.AqaraMessageData
import com.hpdev.smartthermostat.models.aqara.AqaraNetCommand
import com.hpdev.smartthermostat.models.aqara.AqaraNetMessage
import com.hpdev.smartthermostat.models.asHumidity
import com.hpdev.smartthermostat.models.asPressure
import com.hpdev.smartthermostat.models.asTemperature
import com.hpdev.smartthermostat.models.asTemperatureSensor
import com.hpdev.smartthermostat.network.UDPMessenger
import com.hpdev.smartthermostat.network.sendAndReceiveMessage
import com.hpdev.smartthermostat.utils.Retriable
import com.hpdev.smartthermostatcore.network.ObjectParser
import com.hpdev.smartthermostatcore.network.parseJson
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