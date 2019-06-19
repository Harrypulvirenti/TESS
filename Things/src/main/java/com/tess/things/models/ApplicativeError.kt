package com.tess.things.models

import com.tess.core.models.GenericError

class MalformedIPError(message: String, e: Throwable?) : GenericError(message, e)

class TimeoutError(message: String) : GenericError(message)

class RetryError(message: String) : GenericError(message)