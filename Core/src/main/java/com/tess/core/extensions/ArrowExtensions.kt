package com.tess.core.extensions

import arrow.core.Either
import com.tess.architecture.sdk.utils.SmartLogger
import com.tess.core.models.ApplicativeError
import com.tess.core.models.GenericError

inline fun <E : ApplicativeError, T : Any> Either<E, T>.consume(consumer: (T) -> Unit) =
    this.fold(
        {
            when (it) {
                is GenericError -> {
                    SmartLogger.e(
                        errorMessage = it.message,
                        throwable = it.e
                    )
                }
                else -> {
                    SmartLogger.e(
                        errorMessage = "Consume Either failure: $it"
                    )
                }
            }
        },
        consumer
    )
