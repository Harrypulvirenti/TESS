package com.hpdev.smartthermostat.dataprovider

import com.hpdev.smartthermostat.models.AqaraMessage

interface TemperatureUpdater {

    suspend fun notifyTemperatureUpdate(temperature: AqaraMessage)
}