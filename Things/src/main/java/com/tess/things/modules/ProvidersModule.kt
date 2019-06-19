package com.tess.things.modules

import com.tess.things.interfaces.DataProvider
import com.tess.things.interfaces.DataSubscriber
import com.tess.things.interfaces.DataUpdater
import com.tess.things.models.AqaraSensor
import com.tess.things.models.IP
import com.tess.things.models.Temperature
import com.tess.things.service.AqaraSensorDiscoveryService
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.channels.ReceiveChannel
import org.koin.core.qualifier.named
import org.koin.dsl.module

const val TEMPERATURE_UPDATER = "temperature-updater"
const val TEMPERATURE_SUBSCRIBER = "temperature-subscriber"
const val IP_UPDATER = "ip-updater"
const val IP_SUBSCRIBER = "ip-subscriber"
const val SENSOR_SUBSCRIBER = "sensor-subscriber"

private val temperatureProvider = object : DataProvider<Temperature> {

    override val dataChannel: BroadcastChannel<Temperature> = BroadcastChannel(2)

    override fun subscribeDataUpdate(): ReceiveChannel<Temperature> = dataChannel.openSubscription()

    override suspend fun notifyDataUpdate(data: Temperature) {
        dataChannel.send(data)
    }
}

private val ipProvider = object : DataProvider<IP> {

    override val dataChannel: ConflatedBroadcastChannel<IP> = ConflatedBroadcastChannel()

    override fun subscribeDataUpdate(): ReceiveChannel<IP> = dataChannel.openSubscription()

    override suspend fun notifyDataUpdate(data: IP) {
        dataChannel.send(data)
    }
}

val providersModule = module {

    single<DataUpdater<Temperature>>(named(TEMPERATURE_UPDATER)) { temperatureProvider }

    single<DataSubscriber<Temperature>>(named(TEMPERATURE_SUBSCRIBER)) { temperatureProvider }

    single<DataUpdater<IP>>(named(IP_UPDATER)) { ipProvider }

    single<DataSubscriber<IP>>(named(IP_SUBSCRIBER)) { ipProvider }

    single<DataSubscriber<List<AqaraSensor>>>(named(SENSOR_SUBSCRIBER)) { get<AqaraSensorDiscoveryService>() }
}