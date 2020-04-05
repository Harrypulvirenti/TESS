package com.tess.things.database.repository

import com.tess.architecture.sdk.interfaces.CoroutineHandler
import com.tess.things.database.dao.AqaraMessageDAO
import com.tess.things.models.AqaraMessage
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class AqaraMessageRepositoryImpl(private val dao: AqaraMessageDAO) : AqaraMessageRepository, CoroutineHandler {

    override val job = Job()

    override fun storeMessage(message: AqaraMessage) {
        launch(Default) {
            dao.insertMessage(message)
        }
    }

    override suspend fun getAllMessages(): List<AqaraMessage> {

        return dao.getAllMessages()
    }
}
