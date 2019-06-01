package com.hpdev.smartthermostat.models.aqara

data class AqaraNetCommand(
    val cmd: String,
    val sid: String? = null
)