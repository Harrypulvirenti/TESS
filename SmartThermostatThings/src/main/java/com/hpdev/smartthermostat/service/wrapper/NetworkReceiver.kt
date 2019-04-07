package com.hpdev.smartthermostat.service.wrapper

import com.hpdev.architecture.sdk.interfaces.CoroutineHandler

interface NetworkReceiver : CoroutineHandler {

    fun startReceiver()

    fun onMessageReceived(data: String)

    fun stopReceiver()
}