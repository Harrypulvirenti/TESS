package com.tess.things.models

import arrow.core.Either
import arrow.core.Try
import java.net.InetAddress

inline class IP(private val ip: String? = null) {

    val value: String
        get() = ip.orEmpty()

    fun asInetAddress(): Either<MalformedIPError, InetAddress> = Try { InetAddress.getByName(value) }.toEither {
        MalformedIPError("IP Format Error: " + it.message, it)
    }
}

fun String?.asIP() =
    IP(this)
