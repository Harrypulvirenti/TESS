package com.tess.shared.interfaces

import android.content.Context

interface ApplicationInitializer {

    fun init(bundle: ApplicationBundle)
}

interface ApplicationStarter

data class ApplicationBundle(val applicationContext: Context, val isDebug: Boolean)
