package com.hpdev.smartthermostatcore.network

import arrow.core.Either
import arrow.core.Try
import com.fasterxml.jackson.databind.ObjectMapper
import com.hpdev.smartthermostatcore.models.ApplicativeError
import com.hpdev.smartthermostatcore.models.GenericError
import kotlin.reflect.KClass

class ObjectParserImpl : ObjectParser {

    private val objectMapper: ObjectMapper = ObjectMapper()

    override fun <T : Any> parseJson(json: String, clazz: KClass<T>): Either<ApplicativeError, T> = Try {
        objectMapper.readValue(json, clazz.java)
    }.toEither { GenericError(it) }

    override fun <T : Any> parseJson(json: Map<String, *>, clazz: KClass<T>): Either<ApplicativeError, T> = Try {
        objectMapper.convertValue(json, clazz.java)
    }.toEither { GenericError(it) }

    override fun <T : Any> toJSONString(obj: T): Either<GenericError, String> = Try {
        objectMapper.writeValueAsString(obj)
    }.toEither { GenericError(it) }

    override fun <T : Any> toJSONBytes(obj: T): Either<GenericError, ByteArray> = Try {
        objectMapper.writeValueAsBytes(obj)
    }.toEither { GenericError(it) }
}

inline fun <reified T : Any> ObjectParser.parseJson(json: String): Either<ApplicativeError, T> =
    this.parseJson(json, T::class)

inline fun <reified T : Any> ObjectParser.parseJson(json: Map<String, *>): Either<ApplicativeError, T> =
    this.parseJson(json, T::class)