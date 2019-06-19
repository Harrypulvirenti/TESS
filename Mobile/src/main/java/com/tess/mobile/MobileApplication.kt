package com.tess.mobile

import android.app.Application
import com.tess.architecture.sdk.extensions.initKoin
import com.tess.things.BuildConfig.DEBUG
import com.tess.core.modules.coreModule

class MobileApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin(
            DEBUG,
            coreModule
        )
    }
}
