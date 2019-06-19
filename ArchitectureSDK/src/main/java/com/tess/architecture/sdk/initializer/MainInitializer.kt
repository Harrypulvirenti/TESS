package com.tess.architecture.sdk.initializer

import com.google.firebase.FirebaseApp
import com.tess.architecture.sdk.interfaces.ApplicationBundle
import com.tess.architecture.sdk.interfaces.ApplicationInitializer
import com.tess.architecture.sdk.utils.SmartLogger

internal class MainInitializer : ApplicationInitializer {

    override fun init(bundle: ApplicationBundle) {
        FirebaseApp.initializeApp(bundle.applicationContext)
        SmartLogger.init(bundle.isDebug)
    }
}