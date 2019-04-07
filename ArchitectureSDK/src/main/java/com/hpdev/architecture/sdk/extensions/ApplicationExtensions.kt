package com.hpdev.architecture.sdk.extensions

import android.app.Application
import com.hpdev.architecture.sdk.interfaces.ApplicationBundle
import com.hpdev.architecture.sdk.interfaces.ApplicationInitializer
import com.hpdev.architecture.sdk.interfaces.ApplicationStarter
import com.hpdev.architecture.sdk.modules.sdkModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.core.module.Module

fun Application.initKoin(isDebug: Boolean = false, vararg modules: Module) {

    startKoin {
        if (isDebug) {
            androidLogger(Level.DEBUG)
        }
        androidContext(this@initKoin)
        modules.toMutableList().let { listModules ->
            listModules.add(sdkModule)
            modules(listModules)
        }
    }
    val bundle = ApplicationBundle(this, isDebug)

    getAllOfType<ApplicationStarter>()
        .filter { it is ApplicationInitializer }
        .map { it as ApplicationInitializer }
        .forEach {
            it.init(bundle)
        }
}