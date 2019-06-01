package com.hpdev.smartthermostat.service

import com.hpdev.architecture.sdk.interfaces.CoroutineHandler
import com.hpdev.smartthermostat.interfaces.DataSubscriber
import com.hpdev.smartthermostat.interfaces.DataUpdater
import com.hpdev.smartthermostat.models.AqaraSensor
import com.hpdev.smartthermostat.models.IP
import com.hpdev.smartthermostat.models.Temperature
import com.hpdev.smartthermostat.network.UDPMessenger
import com.hpdev.smartthermostatcore.network.ObjectParser
import kotlinx.coroutines.Job

class TemperatureSensorServiceImpl(
    private val udpMessenger: UDPMessenger,
    private val objectParser: ObjectParser,
    private val temperatureUpdater: DataUpdater<Temperature>,
    ipSubscriber: DataSubscriber<IP>,
    sensorDiscoverySubscriber: DataSubscriber<List<AqaraSensor>>
) : TemperatureSensorService, CoroutineHandler {

    override val job = Job()

    override fun updateSensorData() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}