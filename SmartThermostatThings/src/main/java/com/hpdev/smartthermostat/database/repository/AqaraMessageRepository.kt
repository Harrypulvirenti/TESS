package com.hpdev.smartthermostat.database.repository

import com.hpdev.architecture.sdk.interfaces.CoroutineHandler
import com.hpdev.smartthermostat.models.AqaraMessage

interface AqaraMessageRepository : CoroutineHandler {

    fun storeMessage(message: AqaraMessage)

    suspend fun getAllMessages(): List<AqaraMessage>
}