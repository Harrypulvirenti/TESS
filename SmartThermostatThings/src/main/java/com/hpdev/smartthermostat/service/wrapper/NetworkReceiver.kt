package com.hpdev.smartthermostat.service.wrapper

import com.hpdev.smartthermostatcore.coroutine.CoroutineHandler

interface NetworkReceiver : CoroutineHandler {

    fun startReceiver()

    fun onMessageReceived(data: String)

    fun stopReceiver()
}