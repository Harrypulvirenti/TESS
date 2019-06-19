package com.tess.things.modules

import com.tess.things.viewmodel.TemperatureSensorViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val viewModelModule = module {

    viewModel { TemperatureSensorViewModel(get(named(TEMPERATURE_SUBSCRIBER))) }
}