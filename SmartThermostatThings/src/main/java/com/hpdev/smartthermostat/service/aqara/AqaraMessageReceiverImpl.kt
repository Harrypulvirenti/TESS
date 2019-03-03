package com.hpdev.smartthermostat.service.aqara

import com.hpdev.netmodels.aqara.AqaraMessageData
import com.hpdev.netmodels.aqara.AqaraNetMessage
import com.hpdev.sdk.logging.SmartLogger
import com.hpdev.smartthermostat.service.wrapper.MulticastReceiver
import com.hpdev.smartthermostatcore.network.ObjectParser
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.launch

private const val MULTICAST_IP_ADDRESS = "224.0.0.50"
private const val MULTICAST_PORT = 9898
private const val MULTICAST_RECEIVER_PORT = 4444
private const val BUFFER_SIZE = 2500

class AqaraMessageReceiverImpl(
    private val objectParser: ObjectParser
) : MulticastReceiver(
    MULTICAST_IP_ADDRESS,
    MULTICAST_PORT,
    MULTICAST_RECEIVER_PORT,
    BUFFER_SIZE
), AqaraMessageReceiver {

    private val messageChannel = BroadcastChannel<AqaraNetMessage>(2)

    override fun onMessageReceived(data: String) {
        launch {

            parseJsonAndFold(data) { message: AqaraNetMessage ->
                message.dataJson?.let {
                    parseJsonAndFold(it) { messageData: AqaraMessageData ->
                        message.data = messageData
                    }
                }

                messageChannel.send(message)
            }
        }
    }

    override fun subscribeMessageReceiver() = messageChannel.openSubscription()

    private inline fun <reified R : Any> parseJsonAndFold(json: String, onSuccess: (R) -> Unit) {
        objectParser.parseJson(json, R::class)
            .fold(
                {
                    SmartLogger.e(it, it.message.orEmpty())
                },
                {
                    onSuccess(it)
                })
    }

    override fun stopReceiver() {
        super.stopReceiver()
        messageChannel.close()
    }
}