package com.hpdev.smartthermostat.modules

import com.hpdev.smartthermostat.interfaces.DataProvider
import com.hpdev.smartthermostat.interfaces.DataSubscriber
import com.hpdev.smartthermostat.interfaces.DataUpdater
import com.hpdev.smartthermostat.models.AqaraMessage
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.ReceiveChannel
import org.koin.dsl.module

private val temperatureProvider = object : DataProvider<AqaraMessage> {

    override val dataChannel: BroadcastChannel<AqaraMessage> = BroadcastChannel(2)

    override fun subscribeDataUpdate(): ReceiveChannel<AqaraMessage> = dataChannel.openSubscription()

    override suspend fun notifyDataUpdate(data: AqaraMessage) {
        dataChannel.send(data)
    }
}

val providersModule = module {

    single<DataUpdater<AqaraMessage>> { temperatureProvider }

    single<DataSubscriber<AqaraMessage>> { temperatureProvider }
}