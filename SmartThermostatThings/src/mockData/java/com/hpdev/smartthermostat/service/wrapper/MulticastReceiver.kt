package com.hpdev.smartthermostat.service.wrapper

import com.hpdev.architecture.sdk.interfaces.CoroutineHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

abstract class MulticastReceiver(
    groupIPAddress: String,
    port: Int,
    receivePort: Int = port,
    bufferSize: Int
) : NetworkReceiver, CoroutineHandler {

    private var json =
        "{\"cmd\":\"heartbeat\",\"model\":\"gateway\",\"sid\":\"f0b4299a4a0e\",\"short_id\":\"0\",\"token\":\"zJCU5kiu5UdPbxpN\",\"data\":\"{\\\"ip\\\":\\\"192.168.1.105\\\"}\"}"

    override val job = Job()

    init {
        sendMessage()
    }

    private fun sendMessage() {
        launch {
            onMessageReceived(json)
            delay(10000)
            sendMessage()
        }
    }
}