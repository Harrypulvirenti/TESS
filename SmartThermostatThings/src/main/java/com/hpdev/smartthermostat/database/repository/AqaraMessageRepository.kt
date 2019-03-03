package com.hpdev.smartthermostat.database.repository

import com.hpdev.smartthermostat.models.AqaraMessage
import com.hpdev.smartthermostatcore.coroutine.CoroutineHandler

interface AqaraMessageRepository : CoroutineHandler {

    fun storeMessage(message: AqaraMessage)

    suspend fun getAllMessages(): List<AqaraMessage>
}