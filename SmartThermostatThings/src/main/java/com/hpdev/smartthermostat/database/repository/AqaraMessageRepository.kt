package com.hpdev.smartthermostat.database.repository

import com.hpdev.smartthermostat.models.AqaraMessage

interface AqaraMessageRepository {

    fun storeMessage(message: AqaraMessage)

    suspend fun getAllMessages(): List<AqaraMessage>
}