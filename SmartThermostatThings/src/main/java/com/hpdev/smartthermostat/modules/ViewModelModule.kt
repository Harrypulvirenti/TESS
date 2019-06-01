package com.hpdev.smartthermostat.modules

import com.hpdev.smartthermostat.viewmodel.TemperatureSensorViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val viewModelModule = module {

    viewModel { TemperatureSensorViewModel(get(named(TEMPERATURE_SUBSCRIBER))) }
}