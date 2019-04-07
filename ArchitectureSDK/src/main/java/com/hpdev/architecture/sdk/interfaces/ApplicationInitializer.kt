package com.hpdev.architecture.sdk.interfaces

import android.content.Context

interface ApplicationInitializer {

    fun init(bundle: ApplicationBundle)
}

data class ApplicationBundle(val applicationContext: Context, val isDebug: Boolean)