package com.hpdev.smartthermostat.service

import arrow.core.Either
import arrow.core.flatMap
import arrow.core.getOrElse
import arrow.core.left
import arrow.core.orNull
import com.hpdev.architecture.sdk.extensions.onNotNull
import com.hpdev.architecture.sdk.interfaces.ApplicationStarter
import com.hpdev.architecture.sdk.interfaces.CoroutineHandler
import com.hpdev.smartthermostat.interfaces.DataSubscriber
import com.hpdev.smartthermostat.models.AqaraSensor
import com.hpdev.smartthermostat.models.IP
import com.hpdev.smartthermostat.models.aqara.AqaraNetCommand
import com.hpdev.smartthermostat.models.aqara.AqaraNetMessage
import com.hpdev.smartthermostat.models.aqara.isAckOf
import com.hpdev.smartthermostat.network.UDPMessenger
import com.hpdev.smartthermostat.network.sendAndReceiveMessage
import com.hpdev.smartthermostat.utils.Retriable
import com.hpdev.smartthermostatcore.models.GenericError
import com.hpdev.smartthermostatcore.network.ObjectParser
import com.hpdev.smartthermostatcore.network.parseJson
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch

private const val GATEWAY_UDP_PORT = 9898
private const val GATEWAY_CMD_ID_LIST = "get_id_list"
private const val GATEWAY_CMD_READ = "read"

class AqaraSensorDiscoveryServiceImpl(
    private val udpMessenger: UDPMessenger,
    private val objectParser: ObjectParser,
    ipSubscriber: DataSubscriber<IP>
) : AqaraSensorDiscoveryService, ApplicationStarter, CoroutineHandler {

    override val job = Job()
    private var currentIP = IP()
    private val discoveredSensorChannel = ConflatedBroadcastChannel<List<AqaraSensor>>()

    init {
        launch(Default) {
            ipSubscriber.subscribeDataUpdate().consumeEach {
                currentIP = it
                startDiscovery()
            }
        }
    }

    override fun subscribeDataUpdate(): ReceiveChannel<List<AqaraSensor>> =
        discoveredSensorChannel.openSubscription()

    override fun discoverySensor() {
        launch(Default) {
            startDiscovery()
        }
    }

    private suspend fun startDiscovery() {
        Retriable {
            udpMessenger.sendAndReceiveMessage<AqaraNetCommand, AqaraNetMessage>(
                currentIP,
                GATEWAY_UDP_PORT,
                AqaraNetCommand(GATEWAY_CMD_ID_LIST)
            )
        }.retryIfIsNotAckOf(GATEWAY_CMD_ID_LIST)
            .flatMap { message ->
                message.dataJson?.let { objectParser.parseJson<List<String>>(it) } ?: left()
            }
            .getOrElse { emptyList() }
            .mapNotNull {
                identifySensor(it)
            }.also {
                discoveredSensorChannel.send(it)
            }
    }

    private suspend fun identifySensor(sensorId: String): AqaraSensor? =
        Retriable {
            udpMessenger.sendAndReceiveMessage<AqaraNetCommand, AqaraNetMessage>(
                currentIP,
                GATEWAY_UDP_PORT,
                AqaraNetCommand(GATEWAY_CMD_READ, sensorId)
            )
        }.retryIfIsNotAckOf(GATEWAY_CMD_READ)
            .map {
                onNotNull(it.sid, it.model) { sid, model ->
                    AqaraSensor(sid, model)
                }
            }.orNull()
}

suspend fun Retriable<GenericError, AqaraNetMessage>.retryIfIsNotAckOf(command: String): Either<GenericError, AqaraNetMessage> =
    this.retryOn { response: AqaraNetMessage ->
        !response.isAckOf(command)
    }