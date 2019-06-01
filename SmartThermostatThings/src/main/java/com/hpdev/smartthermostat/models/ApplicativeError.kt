package com.hpdev.smartthermostat.models

import com.hpdev.smartthermostatcore.models.GenericError

class MalformedIPError(message: String, e: Throwable?) : GenericError(message, e)

class TimeoutError(message: String) : GenericError(message)

class RetryError(message: String) : GenericError(message)