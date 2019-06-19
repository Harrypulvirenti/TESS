package com.tess.core.service

import com.tess.core.models.Setting

interface SettingsService {

    suspend fun getSetting(): Setting
}