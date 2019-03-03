package com.hpdev.smartthermostat.service.aqara

import com.hpdev.netmodels.aqara.AqaraNetMessage
import com.hpdev.smartthermostat.service.wrapper.NetworkReceiver
import kotlinx.coroutines.channels.ReceiveChannel

interface AqaraMessageReceiver : NetworkReceiver {

    fun subscribeMessageReceiver(): ReceiveChannel<AqaraNetMessage>
}