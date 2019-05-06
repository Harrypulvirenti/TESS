package com.hpdev.smartthermostat.viewmodel

import androidx.lifecycle.ViewModel
import arrow.core.Either
import com.hpdev.architecture.sdk.extensions.launch
import com.hpdev.architecture.sdk.utils.SmartLogger
import com.hpdev.netmodels.aqara.AqaraNetCommand
import com.hpdev.netmodels.aqara.AqaraNetCommandResponse
import com.hpdev.smartthermostat.interfaces.DataSubscriber
import com.hpdev.smartthermostat.models.IP
import com.hpdev.smartthermostat.models.Temperature
import com.hpdev.smartthermostat.network.UDPMessenger
import com.hpdev.smartthermostat.network.sendAndReceiveMessage
import com.hpdev.smartthermostatcore.extensions.consume
import com.hpdev.smartthermostatcore.models.NetworkError
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.consumeEach

class TemperatureSensorViewModel(
    private val udpMessenger: UDPMessenger,
    temperatureSubscriber: DataSubscriber<Temperature>,
    ipSubscriber: DataSubscriber<IP>
) : ViewModel() {

    private val ipUpdateSubscription: ReceiveChannel<IP> = ipSubscriber.subscribeDataUpdate()
    private val temperatureSubscription: ReceiveChannel<Temperature> = temperatureSubscriber.subscribeDataUpdate()

    init {

        launch(Dispatchers.Default) {
            temperatureSubscription.consumeEach {
                SmartLogger.d(it.value)
            }
        }

        receiveIpUpdate()
    }

    private fun receiveIpUpdate() = launch(Dispatchers.Default) {
        val cmd = AqaraNetCommand("get_id_list")
        val data: Either<NetworkError, AqaraNetCommandResponse> =
            udpMessenger.sendAndReceiveMessage(
                ipUpdateSubscription.receive(),
                9898,
                cmd
            )
        data.consume {
            SmartLogger.d(it)
        }
    }

    override fun onCleared() {
        super.onCleared()
        temperatureSubscription.cancel()
        ipUpdateSubscription.cancel()
    }
}