package com.hpdev.smartthermostatcore.models

sealed class ApplicativeError

open class NetworkError(val message: String) : ApplicativeError()

class GenericError(val e: Throwable) : ApplicativeError()