package com.hpdev.smartthermostat.service.aqara

import com.hpdev.smartthermostat.models.AqaraMessage
import com.hpdev.smartthermostatcore.coroutine.CoroutineHandler
import kotlinx.coroutines.channels.ReceiveChannel

interface AqaraMulticastService : CoroutineHandler {

    fun subscribeReportChannel(): ReceiveChannel<AqaraMessage>

    fun subscribeIpUpdate(): ReceiveChannel<String>

    fun stopService()
}