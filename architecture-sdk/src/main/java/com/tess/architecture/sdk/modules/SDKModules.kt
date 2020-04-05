package com.tess.architecture.sdk.modules

import com.tess.architecture.sdk.initializer.MainInitializer
import com.tess.shared.interfaces.ApplicationInitializer
import org.koin.dsl.module

val sdkModule = module {

    factory<ApplicationInitializer> { MainInitializer() }
}
