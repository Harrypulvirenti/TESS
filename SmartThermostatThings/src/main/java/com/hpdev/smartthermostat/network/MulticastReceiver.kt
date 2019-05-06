package com.hpdev.smartthermostat.network

import com.hpdev.smartthermostat.models.IP

interface MulticastReceiver {

    suspend fun initSocket(
        groupIPAddress: IP,
        port: Int,
        receivePort: Int = port,
        receiver: suspend (String) -> Unit
    )
}