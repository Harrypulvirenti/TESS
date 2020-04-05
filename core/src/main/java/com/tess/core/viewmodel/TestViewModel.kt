package com.tess.core.viewmodel

import androidx.lifecycle.ViewModel
import com.tess.architecture.sdk.utils.SmartLogger
import com.tess.core.service.SettingsService
import com.tess.extensions.android.launch
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TestViewModel(private val serviceSettings: SettingsService) : ViewModel() {

    fun getData() {
        launch(Dispatchers.IO) {
            val setting = serviceSettings.getSetting()
            withContext(Dispatchers.Main) {
                SmartLogger.d(setting.name)
            }
        }
    }
}
