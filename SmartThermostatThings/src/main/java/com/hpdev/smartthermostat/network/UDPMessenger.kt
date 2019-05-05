package com.hpdev.smartthermostat.network

interface UDPMessenger {

    fun setDestinationIP(ip: String)

    fun <T : Any> sendMessage(message: T, port: Int)
}