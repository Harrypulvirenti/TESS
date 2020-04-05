package com.tess.things.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.tess.things.models.AQARA_NETWORK_MESSAGES_TABLE
import com.tess.things.models.AqaraMessage

@Dao
interface AqaraMessageDAO {

    @Insert
    suspend fun insertMessage(data: AqaraMessage)

    @Query("SELECT * FROM $AQARA_NETWORK_MESSAGES_TABLE")
    suspend fun getAllMessages(): List<AqaraMessage>
}
