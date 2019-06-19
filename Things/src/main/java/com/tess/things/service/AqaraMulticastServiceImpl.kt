package com.tess.things.service

import com.tess.architecture.sdk.extensions.isNotNullOrEmpty
import com.tess.architecture.sdk.extensions.takeIfOrElse
import com.tess.architecture.sdk.interfaces.ApplicationStarter
import com.tess.architecture.sdk.interfaces.CoroutineHandler
import com.tess.architecture.sdk.utils.SmartLogger
import com.tess.architecture.sdk.utils.timestamp
import com.tess.things.database.repository.AqaraMessageRepository
import com.tess.things.interfaces.DataUpdater
import com.tess.things.models.AqaraMessage
import com.tess.things.models.IP
import com.tess.things.models.Temperature
import com.tess.things.models.aqara.AqaraNetMessage
import com.tess.things.models.asIP
import com.tess.things.models.asTemperature
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch

private const val REPORT_CMD = "report"

class AqaraMulticastServiceImpl(
    receiver: AqaraMessageReceiver,
    private val repository: AqaraMessageRepository,
    private val temperatureUpdater: DataUpdater<Temperature>,
    private val ipUpdater: DataUpdater<IP>
) : AqaraMulticastService, CoroutineHandler, ApplicationStarter {

    private val messageReceiverChannel: ReceiveChannel<AqaraNetMessage> = receiver.subscribeMessageReceiver()

    private val messageHandlerActions: Map<(AqaraMessage) -> Boolean, (AqaraMessage) -> Unit> = mapOf(
        { message: AqaraMessage -> message.commandName == REPORT_CMD } to
            { message: AqaraMessage ->
                launch(Default) {
                    temperatureUpdater.notifyDataUpdate(message.temperature.asTemperature())
                }
                Unit
            },
        { message: AqaraMessage -> message.ip.isNotNullOrEmpty() } to
            { message: AqaraMessage ->
                launch(Default) {
                    if (currentIp != message.ip) {
                        currentIp = message.ip
                        ipUpdater.notifyDataUpdate(message.ip.asIP())
                    }
                }
                Unit
            }
    )

    override val job = Job()

    private var currentIp: String? = null

    init {
        launch(Default) {
            messageReceiverChannel.consumeEach {
                onMessageReceived(it)
            }
        }
    }

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
}