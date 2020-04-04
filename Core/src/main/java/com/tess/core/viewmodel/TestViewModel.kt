package com.tess.core.viewmodel

import androidx.lifecycle.ViewModel
import com.tess.architecture.sdk.extensions.launch
import com.tess.architecture.sdk.utils.SmartLogger
import com.tess.core.service.SettingsService
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
