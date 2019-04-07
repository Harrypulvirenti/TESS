package com.hpdev.architecture.sdk.initializer

import com.google.firebase.FirebaseApp
import com.hpdev.architecture.sdk.interfaces.ApplicationBundle
import com.hpdev.architecture.sdk.interfaces.ApplicationInitializer
import com.hpdev.architecture.sdk.utils.SmartLogger

internal class MainInitializer : ApplicationInitializer {

    override fun init(bundle: ApplicationBundle) {
        FirebaseApp.initializeApp(bundle.applicationContext)
        SmartLogger.init(bundle.isDebug)
    }
}