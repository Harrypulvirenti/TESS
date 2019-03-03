package com.hpdev.netmodels.aqara

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class AqaraNetMessage(
    @JsonProperty("cmd") val commandName: String? = null,
    @JsonProperty("model") val model: String? = null,
    @JsonProperty("sid") val sid: String? = null,
    @JsonProperty("short_id") val shortId: String? = null,
    @JsonProperty("token") val token: String? = null,
    @JsonProperty("data") val dataJson: String? = null
) {
    var data: AqaraMessageData? = null
}