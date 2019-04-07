package com.hpdev.smartthermostat

import android.app.Application
import com.hpdev.architecture.sdk.extensions.initKoin
import com.hpdev.smartthermostat.BuildConfig.DEBUG
import com.hpdev.smartthermostat.modules.aqaraModule
import com.hpdev.smartthermostat.modules.databaseModule
import com.hpdev.smartthermostat.modules.providersModule
import com.hpdev.smartthermostat.modules.repositoryModule
import com.hpdev.smartthermostat.modules.viewModelModule
import com.hpdev.smartthermostatcore.modules.coreModule
import com.hpdev.smartthermostatcore.modules.networkModule

class ThingsApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        initKoin(
            DEBUG,
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