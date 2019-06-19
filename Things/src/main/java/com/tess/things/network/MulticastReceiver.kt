package com.tess.things.network

import com.tess.things.models.IP

interface MulticastReceiver {

    suspend fun initSocket(
        groupIPAddress: IP,
        port: Int,
        receivePort: Int = port,
        receiver: suspend (String) -> Unit
    )
}