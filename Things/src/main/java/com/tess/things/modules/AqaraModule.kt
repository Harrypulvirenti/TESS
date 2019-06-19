package com.tess.things.modules

import com.tess.architecture.sdk.interfaces.ApplicationStarter
import com.tess.things.network.MulticastReceiver
import com.tess.things.network.MulticastReceiverImpl
import com.tess.things.network.UDPMessenger
import com.tess.things.network.UDPMessengerImpl
import com.tess.things.service.AqaraMessageReceiver
import com.tess.things.service.AqaraMessageReceiverImpl
import com.tess.things.service.AqaraMulticastService
import com.tess.things.service.AqaraMulticastServiceImpl
import com.tess.things.service.AqaraSensorDiscoveryService
import com.tess.things.service.AqaraSensorDiscoveryServiceImpl
import com.tess.things.service.TemperatureSensorService
import com.tess.things.service.TemperatureSensorServiceImpl
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module

val aqaraModule = module {

    factory<MulticastReceiver> { MulticastReceiverImpl() }

    factory<UDPMessenger> { UDPMessengerImpl(get()) }

    single<AqaraMessageReceiver> {
        AqaraMessageReceiverImpl(
            get(),
            get()
        )
    }

    single<AqaraMulticastService> {
        AqaraMulticastServiceImpl(
            get(),
            get(),
            get(named(TEMPERATURE_UPDATER)),
            get(named(IP_UPDATER))
        )
    } bind ApplicationStarter::class

    single<AqaraSensorDiscoveryService> {
        AqaraSensorDiscoveryServiceImpl(get(), get(), get(named(IP_SUBSCRIBER)))
    } bind ApplicationStarter::class

    single<TemperatureSensorService> {
        TemperatureSensorServiceImpl(
            get(),
            get(),
            get(named(TEMPERATURE_UPDATER)),
            get(named(IP_SUBSCRIBER)),
            get(named(SENSOR_SUBSCRIBER))
        )
    } bind ApplicationStarter::class
}