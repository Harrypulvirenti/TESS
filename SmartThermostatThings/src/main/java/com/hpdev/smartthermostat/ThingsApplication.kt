package com.hpdev.smartthermostat

import android.app.Application
import com.hpdev.smartthermostat.BuildConfig.DEBUG
import com.hpdev.smartthermostat.modules.aqaraModule
import com.hpdev.smartthermostat.modules.databaseModule
import com.hpdev.smartthermostat.modules.providersModule
import com.hpdev.smartthermostat.modules.repositoryModule
import com.hpdev.smartthermostat.modules.viewModelModule
import com.hpdev.smartthermostatcore.managers.initCoreComponent
import com.hpdev.smartthermostatcore.modules.coreModule
import com.hpdev.smartthermostatcore.modules.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class ThingsApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initCoreComponent(this, DEBUG)


        startKoin {
            if (DEBUG) {
                androidLogger(Level.DEBUG)
            }

            androidContext(this@ThingsApplication)
            modules(
                providersModule,
                coreModule,
                networkModule,
                aqaraModule,
                databaseModule,
                repositoryModule,
                viewModelModule
            )
        }
    }
}