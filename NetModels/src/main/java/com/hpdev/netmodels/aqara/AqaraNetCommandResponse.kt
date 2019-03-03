package com.hpdev.netmodels.aqara

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class AqaraNetCommandResponse(
    @JsonProperty("cmd") val command: String
)