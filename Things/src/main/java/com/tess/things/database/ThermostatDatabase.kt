package com.tess.things.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tess.things.database.dao.AqaraMessageDAO
import com.tess.things.models.AqaraMessage

@Database(entities = [AqaraMessage::class], version = 1)
abstract class ThermostatDatabase : RoomDatabase() {

    abstract fun aqaraMessageDAO(): AqaraMessageDAO
}