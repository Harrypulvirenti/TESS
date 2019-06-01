package com.hpdev.smartthermostat.models.aqara

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class AqaraMessageData(
    @JsonProperty("temperature") val temperature: String? = null,
    @JsonProperty("pressure") val pressure: String? = null,
    @JsonProperty("humidity") val humidity: String? = null,
    @JsonProperty("voltage") val voltage: String? = null,
    @JsonProperty("rgb") val rgb: String? = null,
    @JsonProperty("illumination") val illumination: String? = null,
    @JsonProperty("ip") val ip: String? = null
)