package com.tess.mobile

import android.app.Application
import com.tess.architecture.sdk.extensions.initKoin
import com.tess.core.modules.coreModule
import com.tess.core.modules.networkModule
import com.tess.features.login.di.loginModule
import com.tess.mobile.BuildConfig.DEBUG

class MobileApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin(
            DEBUG,
            networkModule,
            coreModule,
            loginModule
        )
    }
}
