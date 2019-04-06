package com.hpdev.smartthermostat.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hpdev.netmodels.aqara.AqaraNetCommand
import com.hpdev.sdk.logging.SmartLogger
import com.hpdev.smartthermostat.dataprovider.TemperatureSubscriber
import com.hpdev.smartthermostat.models.AqaraMessage
import com.hpdev.smartthermostat.service.aqara.AqaraMulticastService
import com.hpdev.smartthermostat.service.wrapper.UDPMessenger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

class TemperatureSensorViewModel(
    private val multicastService: AqaraMulticastService,
    private val udpMessenger: UDPMessenger,
    temperatureSubscriber: TemperatureSubscriber
) : ViewModel() {

    private val ipUpdateSubscription: ReceiveChannel<String> = multicastService.subscribeIpUpdate()
    private val temperatureSubscription: ReceiveChannel<AqaraMessage> =
        temperatureSubscriber.subscribeTemperatureUpdate()

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

private fun ViewModel.launch(
    context: CoroutineContext = EmptyCoroutineContext,
    start: CoroutineStart = CoroutineStart.DEFAULT,
    block: suspend CoroutineScope.() -> Unit
) = viewModelScope.launch(context, start, block)