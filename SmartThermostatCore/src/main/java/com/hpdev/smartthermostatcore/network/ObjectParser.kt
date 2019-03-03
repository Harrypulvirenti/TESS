package com.hpdev.smartthermostatcore.network

import arrow.core.Try
import kotlin.reflect.KClass

interface ObjectParser {

    fun <T : Any> parseJson(json: String, clazz: KClass<T>): Try<T>

    fun <T : Any> toJSONString(obj: T): String

    fun <T : Any> toJSONBytes(obj: T): ByteArray
}