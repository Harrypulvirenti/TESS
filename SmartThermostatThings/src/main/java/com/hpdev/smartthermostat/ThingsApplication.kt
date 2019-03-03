package com.hpdev.smartthermostat

import android.app.Application
import com.hpdev.smartthermostat.BuildConfig.DEBUG
import com.hpdev.smartthermostat.modules.aqaraModule
import com.hpdev.smartthermostat.modules.databaseModule
import com.hpdev.smartthermostat.modules.repositoryModule
import com.hpdev.smartthermostat.modules.viewModelModule
import com.hpdev.smartthermostatcore.managers.initCoreComponent
import com.hpdev.smartthermostatcore.modules.coreModule
import com.hpdev.smartthermostatcore.modules.networkModule
import org.koin.android.ext.android.startKoin

class ThingsApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initCoreComponent(this, DEBUG)

        startKoin(
            this, listOf(
                coreModule,
                networkModule,
                aqaraModule,
                databaseModule,
                repositoryModule,
                viewModelModule
            )
        )
    }
}