package com.hpdev.smartthermostat.service.aqara

import com.hpdev.architecture.sdk.interfaces.CoroutineHandler
import com.hpdev.architecture.sdk.utils.SmartLogger
import com.hpdev.netmodels.aqara.AqaraMessageData
import com.hpdev.netmodels.aqara.AqaraNetMessage
import com.hpdev.smartthermostat.service.wrapper.MulticastReceiver
import com.hpdev.smartthermostatcore.network.ObjectParser
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

    override suspend fun onNetworkMessageReceived(data: String) {
        withContext(Default) {
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
                    SmartLogger.e(it.message.orEmpty(), it)
                },
                {
                    onSuccess(it)
                })
    }
}