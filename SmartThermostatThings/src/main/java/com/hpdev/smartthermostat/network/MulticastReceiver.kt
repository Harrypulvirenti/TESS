package com.hpdev.smartthermostat.network

interface MulticastReceiver {

    suspend fun initSocket(
        groupIPAddress: String,
        port: Int,
        receivePort: Int = port,
        receiver: suspend (String) -> Unit
    )
}