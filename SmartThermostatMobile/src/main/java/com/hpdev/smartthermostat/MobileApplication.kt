package com.hpdev.smartthermostat

import android.app.Application
import com.hpdev.architecture.sdk.extensions.initKoin
import com.hpdev.smartthermostat.BuildConfig.DEBUG
import com.hpdev.smartthermostatcore.modules.coreModule

class MobileApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin(
            DEBUG,
            coreModule
        )
    }
}
