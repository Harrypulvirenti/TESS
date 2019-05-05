package com.hpdev.smartthermostat.modules

import com.hpdev.smartthermostat.network.MulticastReceiver
import com.hpdev.smartthermostat.network.MulticastReceiverImpl
import com.hpdev.smartthermostat.network.UDPMessenger
import com.hpdev.smartthermostat.network.UDPMessengerImpl
import com.hpdev.smartthermostat.service.AqaraMessageReceiver
import com.hpdev.smartthermostat.service.AqaraMessageReceiverImpl
import com.hpdev.smartthermostat.service.AqaraMulticastService
import com.hpdev.smartthermostat.service.AqaraMulticastServiceImpl
import org.koin.core.qualifier.named
import org.koin.dsl.module

val aqaraModule = module {

    single<MulticastReceiver> { MulticastReceiverImpl() }

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
    }

    single<UDPMessenger> { UDPMessengerImpl(get()) }
}