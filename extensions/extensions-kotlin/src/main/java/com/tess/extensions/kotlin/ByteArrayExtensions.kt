package com.tess.extensions.kotlin

fun ByteArray.trimToString() =
    this.filter { it != 0.toByte() }.takeIf { it.isNotEmpty() }?.let { String(it.toByteArray()) }
