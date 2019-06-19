package com.tess.core.network

import arrow.core.Either
import arrow.core.Try
import com.fasterxml.jackson.databind.ObjectMapper
import com.tess.core.models.ParsingError
import kotlin.reflect.KClass

class ObjectParserImpl : ObjectParser {

    private val objectMapper: ObjectMapper = ObjectMapper()

    override fun <T : Any> parseJson(json: String, clazz: KClass<T>): Either<ParsingError, T> = Try {
        objectMapper.readValue(json, clazz.java)
    }.toParsingError()

    override fun <T : Any> parseJson(json: Map<String, *>, clazz: KClass<T>): Either<ParsingError, T> = Try {
        objectMapper.convertValue(json, clazz.java)
    }.toParsingError()

    override fun <T : Any> toJSONString(obj: T): Either<ParsingError, String> = Try {
        objectMapper.writeValueAsString(obj)
    }.toParsingError()

    override fun <T : Any> toJSONBytes(obj: T): Either<ParsingError, ByteArray> = Try {
        objectMapper.writeValueAsBytes(obj)
    }.toParsingError()

    private fun <T : Any> Try<T>.toParsingError() =
        this.toEither { ParsingError("Parsing Error: " + it.message, it) }
}

inline fun <reified T : Any> ObjectParser.parseJson(json: String): Either<ParsingError, T> =
    this.parseJson(json, T::class)

inline fun <reified T : Any> ObjectParser.parseJson(json: Map<String, *>): Either<ParsingError, T> =
    this.parseJson(json, T::class)