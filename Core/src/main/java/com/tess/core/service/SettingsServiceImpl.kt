package com.tess.core.service

import com.tess.core.database.SettingsRepository
import com.tess.core.models.Setting

class SettingsServiceImpl(private val settingsRepository: SettingsRepository) : SettingsService {

    override suspend fun getSetting(): Setting {
        return settingsRepository.getDocument("name").await()
    }
}