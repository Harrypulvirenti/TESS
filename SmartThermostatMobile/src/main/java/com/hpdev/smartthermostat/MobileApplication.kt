package com.hpdev.smartthermostat

import android.app.Application
import com.hpdev.smartthermostat.BuildConfig.DEBUG
import com.hpdev.smartthermostatcore.managers.initCoreComponent
import com.hpdev.smartthermostatcore.modules.coreModule
import org.koin.android.ext.android.startKoin

class MobileApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initCoreComponent(this, DEBUG)

        startKoin(this, listOf(coreModule))
    }
}
