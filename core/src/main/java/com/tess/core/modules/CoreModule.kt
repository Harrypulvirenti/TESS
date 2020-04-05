package com.tess.core.modules

import com.tess.core.database.SettingsRepository
import com.tess.core.database.SettingsRepositoryImpl
import com.tess.core.service.SettingsService
import com.tess.core.service.SettingsServiceImpl
import com.tess.core.viewmodel.TestViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val coreModule = module {

    single<SettingsRepository> { SettingsRepositoryImpl(get()) }

    single<SettingsService> { SettingsServiceImpl(get()) }

    viewModel { TestViewModel(get()) }
}
