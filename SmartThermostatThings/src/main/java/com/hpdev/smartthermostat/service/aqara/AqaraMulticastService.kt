package com.hpdev.smartthermostat.service.aqara

import com.hpdev.smartthermostatcore.coroutine.CoroutineHandler
import kotlinx.coroutines.channels.ReceiveChannel

interface AqaraMulticastService : CoroutineHandler {

    fun subscribeIpUpdate(): ReceiveChannel<String>

    fun stopService()
}