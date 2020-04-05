package com.tess.things.service

import com.tess.core.extensions.consume
import com.tess.core.network.ObjectParser
import com.tess.core.network.parseJson
import com.tess.shared.interfaces.CoroutineHandler
import com.tess.things.models.aqara.AqaraMessageData
import com.tess.things.models.aqara.AqaraNetMessage
import com.tess.things.models.asIP
import com.tess.things.network.MulticastReceiver
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
                MULTICAST_IP_ADDRESS.asIP(),
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
                messageChannel.send(message)
            }
        }
    }
}
