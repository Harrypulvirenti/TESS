package com.hpdev.smartthermostat.service

import com.hpdev.architecture.sdk.extensions.isNotNullOrEmpty
import com.hpdev.architecture.sdk.extensions.or
import com.hpdev.architecture.sdk.extensions.takeIfOrElse
import com.hpdev.architecture.sdk.interfaces.ApplicationStarter
import com.hpdev.architecture.sdk.interfaces.CoroutineHandler
import com.hpdev.architecture.sdk.utils.SmartLogger
import com.hpdev.architecture.sdk.utils.timestamp
import com.hpdev.netmodels.aqara.AqaraNetMessage
import com.hpdev.smartthermostat.database.repository.AqaraMessageRepository
import com.hpdev.smartthermostat.interfaces.DataUpdater
import com.hpdev.smartthermostat.models.AqaraMessage
import com.hpdev.smartthermostat.modules.IP
import com.hpdev.smartthermostat.modules.Temperature
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
                    temperatureUpdater.notifyDataUpdate(message.temperature.or("--"))
                }
                Unit
            },
        { message: AqaraMessage -> message.ip.isNotNullOrEmpty() } to
            { message: AqaraMessage ->
                launch(Default) {
                    if (currentIp != message.ip) {
                        currentIp = message.ip
                        ipUpdater.notifyDataUpdate(message.ip.orEmpty())
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