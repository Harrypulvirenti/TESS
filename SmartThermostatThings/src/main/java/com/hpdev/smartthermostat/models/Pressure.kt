package com.hpdev.smartthermostat.models

import com.hpdev.architecture.sdk.extensions.or

private const val PRESSURE_DEFAULT_VALUE = "--"

inline class Pressure(private val pressure: String? = null) {

    val value: String
        get() = pressure.or(PRESSURE_DEFAULT_VALUE)
}

fun String?.asPressure() =
    Pressure(this)