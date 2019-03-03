package com.hpdev.smartthermostatcore.service

import com.hpdev.smartthermostatcore.models.Setting

interface SettingsService {

    suspend fun getSetting(): Setting
}