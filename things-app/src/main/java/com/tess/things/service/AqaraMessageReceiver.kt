package com.tess.things.service

import com.tess.things.models.aqara.AqaraNetMessage
import kotlinx.coroutines.channels.ReceiveChannel

interface AqaraMessageReceiver {

    fun subscribeMessageReceiver(): ReceiveChannel<AqaraNetMessage>

    suspend fun onNetworkMessageReceived(data: String)
}
