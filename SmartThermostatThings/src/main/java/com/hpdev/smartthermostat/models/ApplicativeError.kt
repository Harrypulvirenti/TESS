package com.hpdev.smartthermostat.models

import com.hpdev.smartthermostatcore.models.NetworkError

class MalformedIPError(message: String) : NetworkError(message)

class MessageParsingError(message: String) : NetworkError(message)

class TimeoutError(message: String) : NetworkError(message)