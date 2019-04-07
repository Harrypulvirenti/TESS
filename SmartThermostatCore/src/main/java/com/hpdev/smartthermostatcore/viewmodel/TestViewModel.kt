package com.hpdev.smartthermostatcore.viewmodel

import androidx.lifecycle.ViewModel
import com.hpdev.architecture.sdk.extensions.launch
import com.hpdev.architecture.sdk.utils.SmartLogger
import com.hpdev.smartthermostatcore.service.SettingsService
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