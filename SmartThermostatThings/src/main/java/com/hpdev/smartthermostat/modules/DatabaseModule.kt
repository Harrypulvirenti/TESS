package com.hpdev.smartthermostat.modules

import androidx.room.Room
import com.hpdev.smartthermostat.database.ThermostatDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module.module

const val DATABASE_NAME = "thermostat_db"

val databaseModule = module {

    single {
        Room
            .databaseBuilder(androidApplication(), ThermostatDatabase::class.java, DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .fallbackToDestructiveMigrationOnDowngrade()
            .build()
    }

    single { get<ThermostatDatabase>().aqaraMessageDAO() }
}