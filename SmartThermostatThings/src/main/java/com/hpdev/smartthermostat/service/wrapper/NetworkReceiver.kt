package com.hpdev.smartthermostat.service.wrapper

interface NetworkReceiver {

    fun onMessageReceived(data: String)
}