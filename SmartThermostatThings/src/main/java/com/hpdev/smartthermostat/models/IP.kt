package com.hpdev.smartthermostat.models

import arrow.core.Either
import arrow.core.Try
import java.net.InetAddress

inline class IP(private val ip: String?) {

    val value: String
        get() = ip.orEmpty()

    fun asInetAddress(): Either<MalformedIPError, InetAddress> = Try { InetAddress.getByName(value) }.toEither {
        MalformedIPError("IP Format Error: " + it.message)
    }
}