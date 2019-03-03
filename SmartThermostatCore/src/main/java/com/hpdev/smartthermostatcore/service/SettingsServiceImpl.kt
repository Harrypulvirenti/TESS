package com.hpdev.smartthermostatcore.service

import com.hpdev.smartthermostatcore.database.SettingsRepository
import com.hpdev.smartthermostatcore.models.Setting

class SettingsServiceImpl(private val settingsRepository: SettingsRepository) : SettingsService {

    override suspend fun getSetting(): Setting {
        return settingsRepository.getDocument("name").await()
    }
}