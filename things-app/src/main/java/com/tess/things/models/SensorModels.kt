package com.tess.things.models

sealed class SensorModels

open class AqaraSensor(
    open val id: String,
    open val modelCode: String
) : SensorModels()

data class TemperatureSensor(
    override val id: String,
    override val modelCode: String
) : AqaraSensor(id, modelCode) {

    var temperature = Temperature()
    var humidity = Humidity()
    var pressure = Pressure()
}

fun AqaraSensor.asTemperatureSensor(): TemperatureSensor =
    TemperatureSensor(id, modelCode)
