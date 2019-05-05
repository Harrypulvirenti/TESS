package com.hpdev.smartthermostatcore.models

sealed class ApplicativeError

class GenericError(val e: Throwable) : ApplicativeError()