package com.tess.things.modules

import androidx.room.Room
import com.tess.things.database.ThermostatDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

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
