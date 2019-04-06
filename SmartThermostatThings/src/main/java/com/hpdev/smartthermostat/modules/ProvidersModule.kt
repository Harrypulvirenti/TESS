package com.hpdev.smartthermostat.modules

import com.hpdev.smartthermostat.dataprovider.TemperatureSubscriber
import com.hpdev.smartthermostat.dataprovider.TemperatureUpdater
import com.hpdev.smartthermostat.models.AqaraMessage
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.ReceiveChannel
import org.koin.dsl.module

private val temperatureProvider = object : TemperatureUpdater, TemperatureSubscriber {

    private val temperatureChannel = BroadcastChannel<AqaraMessage>(2)

    override suspend fun notifyTemperatureUpdate(temperature: AqaraMessage) {
        temperatureChannel.send(temperature)
    }

    override fun subscribeTemperatureUpdate(): ReceiveChannel<AqaraMessage> =
        temperatureChannel.openSubscription()
}

val providersModule = module {

    single<TemperatureSubscriber> { temperatureProvider }

    single<TemperatureUpdater> { temperatureProvider }
}