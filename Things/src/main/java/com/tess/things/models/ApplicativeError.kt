package com.tess.things.models

import com.tess.core.models.GenericError

data class MalformedIPError(
    override val message: String,
    override val e: Throwable?
) : GenericError(message, e)

data class TimeoutError(override val message: String) : GenericError(message)

data class RetryError(override val message: String) : GenericError(message)