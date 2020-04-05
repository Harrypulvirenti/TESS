package com.tess.architecture.sdk.initializer

import com.google.firebase.FirebaseApp
import com.tess.architecture.sdk.utils.SmartLogger
import com.tess.shared.interfaces.ApplicationBundle
import com.tess.shared.interfaces.ApplicationInitializer

internal class MainInitializer : ApplicationInitializer {

    override fun init(bundle: ApplicationBundle) {
        FirebaseApp.initializeApp(bundle.applicationContext)
        SmartLogger.init(bundle.isDebug)
    }
}
