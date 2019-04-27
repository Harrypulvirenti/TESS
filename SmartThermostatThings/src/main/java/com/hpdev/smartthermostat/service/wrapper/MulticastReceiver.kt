package com.hpdev.smartthermostat.service.wrapper

interface MulticastReceiver {

    suspend fun initSocket(
        groupIPAddress: String,
        port: Int,
        receivePort: Int = port,
        receiver: suspend (String) -> Unit
    )
}