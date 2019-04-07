package com.hpdev.architecture.sdk.modules

import com.hpdev.architecture.sdk.initializer.MainInitializer
import com.hpdev.architecture.sdk.interfaces.ApplicationInitializer
import org.koin.dsl.module

val sdkModule = module {

    single<ApplicationInitializer> { MainInitializer() }
}