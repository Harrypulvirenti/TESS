package com.hpdev.smartthermostat.service.aqara

import com.hpdev.netmodels.aqara.AqaraNetMessage
import com.hpdev.sdk.datetime.timestamp
import com.hpdev.sdk.extensions.isNotNullOrEmpty
import com.hpdev.sdk.extensions.takeIfOrElse
import com.hpdev.sdk.logging.SmartLogger
import com.hpdev.smartthermostat.database.repository.AqaraMessageRepository
import com.hpdev.smartthermostat.models.AqaraMessage
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.launch

private const val REPORT_CMD = "report"

class AqaraMulticastServiceImpl(
    private val receiver: AqaraMessageReceiver,
    private val repository: AqaraMessageRepository
) : AqaraMulticastService {

    private val reportChannel = BroadcastChannel<AqaraMessage>(2)

    override val job = Job()

    private val ipUpdateChannel = ConflatedBroadcastChannel<String>()

    private val messageReceiverChannel: ReceiveChannel<AqaraNetMessage>

    private val messageHandlerActions: Map<(AqaraMessage) -> Boolean, (AqaraMessage) -> Unit> = mapOf(
        { message: AqaraMessage -> message.commandName == REPORT_CMD } to
            { message: AqaraMessage ->
                launch {
                    reportChannel.send(message)
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

    init {
        receiver.startReceiver()
        messageReceiverChannel = receiver.subscribeMessageReceiver()

        receiveMessage()
    }

    override fun subscribeReportChannel() = reportChannel.openSubscription()

    override fun subscribeIpUpdate(): ReceiveChannel<String> = ipUpdateChannel.openSubscription()

    private fun receiveMessage() = launch {
        handleReceivedMessage(messageReceiverChannel.receive())
    }

    private fun handleReceivedMessage(netMessage: AqaraNetMessage) {
        val message = AqaraMessage(timestamp, netMessage)

        messageHandlerActions.filter { it.key(message) }
            .values
            .takeIfOrElse({ it.isNotEmpty() }, { SmartLogger.d(message) })
            ?.forEach { it(message) }

        storeMessage(message)
        receiveMessage()
    }

    private fun storeMessage(message: AqaraMessage) =
        repository.storeMessage(message)

    override fun stopService() {
        reportChannel.close()
        ipUpdateChannel.close()
        receiver.stopReceiver()
    }
}