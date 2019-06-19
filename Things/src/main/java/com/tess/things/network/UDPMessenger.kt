package com.tess.things.network

import arrow.core.Either
import com.tess.things.models.IP
import com.tess.core.models.GenericError
import kotlin.reflect.KClass

interface UDPMessenger {

    suspend fun <T : Any> sendMessage(ip: IP, port: Int, message: T): Either<GenericError, Unit>

    suspend fun <T : Any, R : Any> sendAndReceiveMessage(
        ip: IP,
        port: Int,
        message: T,
        response: KClass<R>
    ): Either<GenericError, R>
}