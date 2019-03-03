package com.hpdev.smartthermostat.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hpdev.smartthermostat.database.dao.AqaraMessageDAO
import com.hpdev.smartthermostat.models.AqaraMessage

@Database(entities = [AqaraMessage::class], version = 1)
abstract class ThermostatDatabase : RoomDatabase() {

    abstract fun aqaraMessageDAO(): AqaraMessageDAO
}