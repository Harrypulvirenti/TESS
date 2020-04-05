package com.tess.things.database.repository

import com.tess.things.models.AqaraMessage

interface AqaraMessageRepository {

    fun storeMessage(message: AqaraMessage)

    suspend fun getAllMessages(): List<AqaraMessage>
}
