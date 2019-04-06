package com.hpdev.smartthermostat.dataprovider

import com.hpdev.smartthermostat.models.AqaraMessage
import kotlinx.coroutines.channels.ReceiveChannel

interface TemperatureSubscriber {

    fun subscribeTemperatureUpdate(): ReceiveChannel<AqaraMessage>
}