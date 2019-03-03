package com.hpdev.sdk.extensions

fun String?.isNotNullOrEmpty() = !this.isNullOrEmpty()

fun String?.isNotNullOrBlank() = !this.isNullOrBlank()