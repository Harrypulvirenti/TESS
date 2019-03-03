package com.hpdev.smartthermostatcore.managers

import android.content.Context
import com.google.firebase.FirebaseApp
import com.hpdev.sdk.logging.SmartLogger

fun initCoreComponent(applicationContext: Context, isDebug: Boolean) {

    FirebaseApp.initializeApp(applicationContext)
    SmartLogger.init(isDebug)
}