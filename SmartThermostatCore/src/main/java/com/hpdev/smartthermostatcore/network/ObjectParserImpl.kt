package com.hpdev.smartthermostatcore.network

import arrow.core.Try
import com.fasterxml.jackson.databind.ObjectMapper
import kotlin.reflect.KClass

class ObjectParserImpl : ObjectParser {

    private val objectMapper: ObjectMapper = ObjectMapper()

    override fun <T : Any> parseJson(json: String, clazz: KClass<T>): Try<T> = Try {
        objectMapper.readValue(json, clazz.java)
    }

    override fun <T : Any> toJSONString(obj: T): String = objectMapper.writeValueAsString(obj)

    override fun <T : Any> toJSONBytes(obj: T): ByteArray = objectMapper.writeValueAsBytes(obj)
}