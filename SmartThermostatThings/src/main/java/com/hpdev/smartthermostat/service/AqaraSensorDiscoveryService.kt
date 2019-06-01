package com.hpdev.smartthermostat.service

import com.hpdev.architecture.sdk.interfaces.ApplicationStarter
import com.hpdev.smartthermostat.interfaces.DataSubscriber
import com.hpdev.smartthermostat.models.AqaraSensor

interface AqaraSensorDiscoveryService : ApplicationStarter, DataSubscriber<List<AqaraSensor>> {

    fun discoverySensor()
}