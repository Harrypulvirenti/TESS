package com.hpdev.smartthermostat.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hpdev.architecture.sdk.extensions.launch
import com.hpdev.architecture.sdk.utils.SmartLogger
import com.hpdev.netmodels.aqara.AqaraNetCommand
import com.hpdev.smartthermostat.interfaces.DataSubscriber
import com.hpdev.smartthermostat.modules.IP
import com.hpdev.smartthermostat.modules.Temperature
import com.hpdev.smartthermostat.service.wrapper.UDPMessenger
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch

class TemperatureSensorViewModel(
    private val udpMessenger: UDPMessenger,
    temperatureSubscriber: DataSubscriber<Temperature>,
    ipSubscriber: DataSubscriber<IP>
) : ViewModel() {

    private val ipUpdateSubscription: ReceiveChannel<IP> = ipSubscriber.subscribeDataUpdate()
    private val temperatureSubscription: ReceiveChannel<Temperature> = temperatureSubscriber.subscribeDataUpdate()

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
        ipUpdateSubscription.cancel()
    }
}