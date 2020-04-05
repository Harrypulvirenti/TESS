package com.tess.things.models

import com.tess.architecture.sdk.extensions.or

private const val HUMIDITY_DEFAULT_VALUE = "--"

inline class Humidity(private val humidity: String? = null) {

    val value: String
        get() = humidity.or(HUMIDITY_DEFAULT_VALUE)
}

fun String?.asHumidity() =
    Humidity(this)