package com.hpdev.smartthermostatcore.modules

import com.hpdev.smartthermostatcore.database.SettingsRepository
import com.hpdev.smartthermostatcore.database.SettingsRepositoryImpl
import com.hpdev.smartthermostatcore.service.SettingsService
import com.hpdev.smartthermostatcore.service.SettingsServiceImpl
import com.hpdev.smartthermostatcore.viewmodel.TestViewModel
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val coreModule = module {

    single<SettingsRepository> { SettingsRepositoryImpl() }

    single<SettingsService> { SettingsServiceImpl(get()) }

    viewModel { TestViewModel(get()) }
}