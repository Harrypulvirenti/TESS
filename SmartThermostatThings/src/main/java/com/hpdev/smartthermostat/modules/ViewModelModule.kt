package com.hpdev.smartthermostat.modules

import com.hpdev.smartthermostat.viewmodel.TemperatureSensorViewModel
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val viewModelModule = module {

    viewModel { TemperatureSensorViewModel(get(), get()) }
}