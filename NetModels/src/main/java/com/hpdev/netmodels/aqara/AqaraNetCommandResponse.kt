package com.hpdev.netmodels.aqara

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class AqaraNetCommandResponse(
    @JsonProperty("cmd") val command: String? = null,
    @JsonProperty("sid") val sid: String? = null,
    @JsonProperty("token") val token: String? = null,
    @JsonProperty("data") val dataJson: String? = null
)