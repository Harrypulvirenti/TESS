package com.tess.things.interfaces

import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.ReceiveChannel

interface DataProvider<T> : DataSubscriber<T>, DataUpdater<T> {
    val dataChannel: BroadcastChannel<T>
}

interface DataSubscriber<T> {
    fun subscribeDataUpdate(): ReceiveChannel<T>
}

interface DataUpdater<T> {
    suspend fun notifyDataUpdate(data: T)
}