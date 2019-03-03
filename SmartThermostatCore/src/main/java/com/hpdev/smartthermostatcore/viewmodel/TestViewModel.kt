package com.hpdev.smartthermostatcore.viewmodel

import androidx.lifecycle.ViewModel
import com.hpdev.sdk.logging.SmartLogger
import com.hpdev.smartthermostatcore.service.SettingsService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TestViewModel(private val serviceSettings: SettingsService) : ViewModel() {

    fun getData() {
        GlobalScope.launch(Dispatchers.IO) {
            val setting = serviceSettings.getSetting()
            withContext(Dispatchers.Main) {
                SmartLogger.d(setting.name)
            }
        }
    }
}