package com.hpdev.smartthermostat.viewmodel

import androidx.lifecycle.ViewModel
import com.hpdev.architecture.sdk.extensions.launch
import com.hpdev.architecture.sdk.utils.SmartLogger
import com.hpdev.smartthermostat.interfaces.DataSubscriber
import com.hpdev.smartthermostat.models.Temperature
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.consumeEach

class TemperatureSensorViewModel(
    temperatureSubscriber: DataSubscriber<Temperature>
) : ViewModel() {

    private val temperatureSubscription: ReceiveChannel<Temperature> = temperatureSubscriber.subscribeDataUpdate()

    init {

        launch(Dispatchers.Default) {
            temperatureSubscription.consumeEach {
                SmartLogger.d(it.value)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        temperatureSubscription.cancel()
    }
}