package com.tess.core.models

sealed class ApplicativeError

open class GenericError(open val message: String, open val e: Throwable? = null) : ApplicativeError()

data class NetworkError(
    override val message: String,
    override val e: Throwable?
) : GenericError(message, e)

data class ParsingError(
    override val message: String,
    override val e: Throwable?
) : GenericError(message, e)