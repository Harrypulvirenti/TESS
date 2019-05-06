package com.hpdev.smartthermostat.models

import com.hpdev.architecture.sdk.extensions.or

private const val TEMPERATURE_DEFAULT_VALUE = "--"

inline class Temperature(private val temperature: String?) {

    val value: String
        get() = temperature.or(TEMPERATURE_DEFAULT_VALUE)
}