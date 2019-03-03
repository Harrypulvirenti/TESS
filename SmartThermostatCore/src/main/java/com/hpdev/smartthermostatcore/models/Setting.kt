package com.hpdev.smartthermostatcore.models

import com.fasterxml.jackson.annotation.JsonProperty

class Setting(
    @JsonProperty("name") val name: String,
    @JsonProperty("surname") val surname: String
)