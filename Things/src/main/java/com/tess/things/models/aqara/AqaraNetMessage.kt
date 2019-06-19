package com.tess.things.models.aqara

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

private const val ACK_POSTFIX = "_ack"

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

infix fun AqaraNetMessage.isAckOf(command: String): Boolean =
    commandName.orEmpty() == command + ACK_POSTFIX