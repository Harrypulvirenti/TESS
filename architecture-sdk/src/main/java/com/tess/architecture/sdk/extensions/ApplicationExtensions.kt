package com.tess.architecture.sdk.extensions

import android.app.Application
import com.tess.shared.interfaces.ApplicationBundle
import com.tess.shared.interfaces.ApplicationInitializer
import com.tess.shared.interfaces.ApplicationStarter
import com.tess.architecture.sdk.modules.sdkModule
import org.koin.android.ext.android.getKoin
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

    getKoin().getAll<ApplicationInitializer>()
        .forEach {
            it.init(bundle)
        }

    getKoin().getAll<ApplicationStarter>()
}
