package com.hpdev.smartthermostat.service.wrapper

interface UDPMessenger {

    fun setDestinationIP(ip: String)

    fun <T : Any> sendMessage(message: T, port: Int)
}