package com.hpdev.smartthermostat.database.repository

import com.hpdev.smartthermostat.database.dao.AqaraMessageDAO
import com.hpdev.smartthermostat.models.AqaraMessage
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class AqaraMessageRepositoryImpl(private val dao: AqaraMessageDAO) : AqaraMessageRepository {

    override val job = Job()

    override fun storeMessage(message: AqaraMessage) {
        launch {
            dao.insertMessage(message)
        }
    }

    override suspend fun getAllMessages(): List<AqaraMessage> {

        return async { dao.getAllMessages() }.await()
    }
}