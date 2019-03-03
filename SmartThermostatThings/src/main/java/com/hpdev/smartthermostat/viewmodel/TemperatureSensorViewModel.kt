package com.hpdev.smartthermostat.viewmodel

import androidx.lifecycle.ViewModel
import com.hpdev.netmodels.aqara.AqaraNetCommand
import com.hpdev.smartthermostat.service.aqara.AqaraMulticastService
import com.hpdev.smartthermostat.service.wrapper.UDPMessenger
import com.hpdev.smartthermostatcore.viewmodel.SmartViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.launch

class TemperatureSensorViewModel(
    private val multicastService: AqaraMulticastService,
    private val udpMessenger: UDPMessenger
) : ViewModel(), SmartViewModel {

    override val job = Job()

    private val ipUpdateSubscription: ReceiveChannel<String> = multicastService.subscribeIpUpdate()

    init {

        receiveIpUpdate()
    }

    fun requestTemperature() {
    }

    private fun receiveIpUpdate() = launch {
        udpMessenger.setDestinationIP(ipUpdateSubscription.receive())
        val cmd = AqaraNetCommand("get_id_list")
        udpMessenger.sendMessage(cmd, 9898)
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
        multicastService.stopService()
    }
}