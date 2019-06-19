package com.tess.things.models.aqara

data class AqaraNetCommand(
    val cmd: String,
    val sid: String? = null
)