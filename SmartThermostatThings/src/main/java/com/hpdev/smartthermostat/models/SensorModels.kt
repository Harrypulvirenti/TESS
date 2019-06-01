package com.hpdev.smartthermostat.models

sealed class SensorModels

open class AqaraSensor(
    open val id: String,
    open val modelCode: String
) : SensorModels()

data class TemperatureSensor(
    override val id: String,
    override val modelCode: String,
    val temperature: String,
    val humidity: String,
    val pressure: String
) : AqaraSensor(id, modelCode)
