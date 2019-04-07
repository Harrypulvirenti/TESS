package com.hpdev.smartthermostat.service.aqara

import com.hpdev.architecture.sdk.extensions.isNotNullOrEmpty
import com.hpdev.architecture.sdk.extensions.takeIfOrElse
import com.hpdev.architecture.sdk.utils.SmartLogger
import com.hpdev.architecture.sdk.utils.timestamp
import com.hpdev.netmodels.aqara.AqaraNetMessage
import com.hpdev.smartthermostat.database.repository.AqaraMessageRepository
import com.hpdev.smartthermostat.dataprovider.TemperatureUpdater
import com.hpdev.smartthermostat.models.AqaraMessage
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch

private const val REPORT_CMD = "report"

class AqaraMulticastServiceImpl(
    private val receiver: AqaraMessageReceiver,
    private val repository: AqaraMessageRepository,
    private val temperatureUpdater: TemperatureUpdater
) : AqaraMulticastService {

    private val ipUpdateChannel = ConflatedBroadcastChannel<String>()

    private val messageReceiverChannel: ReceiveChannel<AqaraNetMessage> = receiver.subscribeMessageReceiver()

    private val messageHandlerActions: Map<(AqaraMessage) -> Boolean, (AqaraMessage) -> Unit> = mapOf(
        { message: AqaraMessage -> message.commandName == REPORT_CMD } to
            { message: AqaraMessage ->
                launch {
                    temperatureUpdater.notifyTemperatureUpdate(message)
                }
                Unit
            },
        { message: AqaraMessage -> message.ip.isNotNullOrEmpty() } to
            { message: AqaraMessage ->
                launch {
                    if (ipUpdateChannel.valueOrNull != message.ip)
                        ipUpdateChannel.send(message.ip!!)
                }
                Unit
            }
    )

    override val job = Job()

    init {
        receiver.startReceiver()

        launch {
            messageReceiverChannel.consumeEach {
                onMessageReceived(it)
            }
        }
    }

    override fun subscribeIpUpdate(): ReceiveChannel<String> = ipUpdateChannel.openSubscription()

    private fun onMessageReceived(netMessage: AqaraNetMessage) {
        val message = AqaraMessage(timestamp, netMessage)

        messageHandlerActions.filter { it.key(message) }
            .values
            .takeIfOrElse({ it.isNotEmpty() }, { SmartLogger.d(message) })
            ?.forEach { it(message) }

        storeMessage(message)
    }

    private fun storeMessage(message: AqaraMessage) =
        repository.storeMessage(message)

    override fun stopService() {
        ipUpdateChannel.close()
        receiver.stopReceiver()
    }
}