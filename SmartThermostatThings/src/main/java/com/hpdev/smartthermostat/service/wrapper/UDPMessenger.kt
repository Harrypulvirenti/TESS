package com.hpdev.smartthermostat.service.wrapper

import com.hpdev.architecture.sdk.interfaces.CoroutineHandler

interface UDPMessenger : CoroutineHandler {

    fun setDestinationIP(ip: String)

    fun <T : Any> sendMessage(message: T, port: Int)
}