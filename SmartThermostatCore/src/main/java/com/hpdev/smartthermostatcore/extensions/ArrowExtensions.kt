package com.hpdev.smartthermostatcore.extensions

import arrow.core.Either
import com.hpdev.architecture.sdk.utils.SmartLogger
import com.hpdev.smartthermostatcore.models.ApplicativeError
import com.hpdev.smartthermostatcore.models.GenericError

fun <E : ApplicativeError, T : Any> Either<E, T>.consume(consumer: (T) -> Unit) =
    this.fold(
        {
            when (it) {
                is GenericError -> {
                    SmartLogger.e(
                        errorMessage = "Consume Either failure: " + it.e.message.orEmpty(),
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