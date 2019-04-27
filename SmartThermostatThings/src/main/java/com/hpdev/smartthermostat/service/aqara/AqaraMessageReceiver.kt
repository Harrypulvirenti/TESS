package com.hpdev.smartthermostat.service.aqara

import com.hpdev.netmodels.aqara.AqaraNetMessage
import kotlinx.coroutines.channels.ReceiveChannel

interface AqaraMessageReceiver {

    fun subscribeMessageReceiver(): ReceiveChannel<AqaraNetMessage>

    suspend fun onNetworkMessageReceived(data: String)
}