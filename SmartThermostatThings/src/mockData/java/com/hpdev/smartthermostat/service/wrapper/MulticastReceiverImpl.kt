package com.hpdev.smartthermostat.service.wrapper

import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class MulticastReceiverImpl : MulticastReceiver {
    private var json =
        "{\"cmd\":\"heartbeat\",\"model\":\"gateway\",\"sid\":\"f0b4299a4a0e\",\"short_id\":\"0\",\"token\":\"zJCU5kiu5UdPbxpN\",\"data\":\"{\\\"ip\\\":\\\"192.168.1.105\\\"}\"}"

    override suspend fun initSocket(
        groupIPAddress: String,
        port: Int,
        receivePort: Int,
        receiver: suspend (String) -> Unit
    ) {
        sendMessage(receiver)
    }

    private suspend fun sendMessage(receiver: suspend (String) -> Unit) {
        withContext(Default) {
            receiver(json)
            delay(10000)
            sendMessage(receiver)
        }
    }
}
