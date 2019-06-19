package com.tess.core.models

sealed class ApplicativeError

open class GenericError(val message: String, val e: Throwable? = null) : ApplicativeError()

class NetworkError(message: String, e: Throwable?) : GenericError(message, e)

class ParsingError(message: String, e: Throwable?) : GenericError(message, e)