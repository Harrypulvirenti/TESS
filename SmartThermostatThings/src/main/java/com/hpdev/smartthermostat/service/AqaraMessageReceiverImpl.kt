package com.hpdev.smartthermostat.service

import com.hpdev.architecture.sdk.interfaces.CoroutineHandler
import com.hpdev.netmodels.aqara.AqaraMessageData
import com.hpdev.netmodels.aqara.AqaraNetMessage
import com.hpdev.smartthermostat.network.MulticastReceiver
import com.hpdev.smartthermostatcore.extensions.consume
import com.hpdev.smartthermostatcore.network.ObjectParser
import com.hpdev.smartthermostatcore.network.parseJson
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val MULTICAST_IP_ADDRESS = "224.0.0.50"
private const val MULTICAST_PORT = 9898
private const val MULTICAST_RECEIVER_PORT = 4444

class AqaraMessageReceiverImpl(
    private val multicastReceiver: MulticastReceiver,
    private val objectParser: ObjectParser
) : AqaraMessageReceiver, CoroutineHandler {

    private val messageChannel = BroadcastChannel<AqaraNetMessage>(2)
    override val job = Job()

    init {

        launch(Default) {

            multicastReceiver.initSocket(
                MULTICAST_IP_ADDRESS,
                MULTICAST_PORT,
                MULTICAST_RECEIVER_PORT,
                this@AqaraMessageReceiverImpl::onNetworkMessageReceived
            )
        }
    }

    override fun subscribeMessageReceiver() = messageChannel.openSubscription()

    override suspend fun onNetworkMessageReceived(data: String) {
        withContext(Default) {

            objectParser.parseJson<AqaraNetMessage>(data).consume { message: AqaraNetMessage ->
                message.dataJson?.let {
                    objectParser.parseJson<AqaraMessageData>(it).consume { messageData: AqaraMessageData ->
                        message.data = messageData
                    }
                }
                sendParsedMessage(message)
            }
        }
    }

    private fun sendParsedMessage(message: AqaraNetMessage) {
        launch(Default) {
            messageChannel.send(message)
        }
    }
}