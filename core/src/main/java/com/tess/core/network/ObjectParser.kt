package com.tess.core.network

import arrow.core.Either
import com.tess.core.models.ParsingError
import kotlin.reflect.KClass

interface ObjectParser {

    fun <T : Any> parseJson(json: String, clazz: KClass<T>): Either<ParsingError, T>

    fun <T : Any> parseJson(json: Map<String, *>, clazz: KClass<T>): Either<ParsingError, T>

    fun <T : Any> toJSONString(obj: T): Either<ParsingError, String>

    fun <T : Any> toJSONBytes(obj: T): Either<ParsingError, ByteArray>
}
