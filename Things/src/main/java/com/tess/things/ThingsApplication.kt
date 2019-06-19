package com.tess.things

import android.app.Application
import com.tess.architecture.sdk.extensions.initKoin
import com.tess.things.BuildConfig.DEBUG
import com.tess.things.modules.aqaraModule
import com.tess.things.modules.databaseModule
import com.tess.things.modules.providersModule
import com.tess.things.modules.repositoryModule
import com.tess.things.modules.viewModelModule
import com.tess.core.modules.coreModule
import com.tess.core.modules.networkModule

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