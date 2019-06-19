package com.tess.core.models

import com.fasterxml.jackson.annotation.JsonProperty

class Setting(
    @JsonProperty("name") val name: String,
    @JsonProperty("surname") val surname: String
)