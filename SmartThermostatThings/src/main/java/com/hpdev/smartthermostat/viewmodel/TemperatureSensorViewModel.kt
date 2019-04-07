package com.hpdev.smartthermostat.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hpdev.architecture.sdk.extensions.launch
import com.hpdev.architecture.sdk.utils.SmartLogger
import com.hpdev.netmodels.aqara.AqaraNetCommand
import com.hpdev.smartthermostat.interfaces.DataSubscriber
import com.hpdev.smartthermostat.models.AqaraMessage
import com.hpdev.smartthermostat.service.aqara.AqaraMulticastService
import com.hpdev.smartthermostat.service.wrapper.UDPMessenger
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch

class TemperatureSensorViewModel(
    private val multicastService: AqaraMulticastService,
    private val udpMessenger: UDPMessenger,
    temperatureSubscriber: DataSubscriber<AqaraMessage>
) : ViewModel() {

    private val ipUpdateSubscription: ReceiveChannel<String> = multicastService.subscribeIpUpdate()
    private val temperatureSubscription: ReceiveChannel<AqaraMessage> = temperatureSubscriber.subscribeDataUpdate()

    init {

        viewModelScope.launch {
            temperatureSubscription.consumeEach {
                SmartLogger.d(it)
            }
        }
    }

    private fun receiveIpUpdate() = launch {
        udpMessenger.setDestinationIP(ipUpdateSubscription.receive())
        val cmd = AqaraNetCommand("get_id_list")
        udpMessenger.sendMessage(cmd, 9898)
    }

    override fun onCleared() {
        super.onCleared()
        temperatureSubscription.cancel()
        multicastService.stopService()
    }
}