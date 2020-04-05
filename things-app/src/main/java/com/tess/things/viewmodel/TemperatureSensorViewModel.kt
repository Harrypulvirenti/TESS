package com.tess.things.viewmodel

import androidx.lifecycle.ViewModel
import com.tess.architecture.sdk.extensions.launch
import com.tess.architecture.sdk.utils.SmartLogger
import com.tess.things.interfaces.DataSubscriber
import com.tess.things.models.Temperature
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
