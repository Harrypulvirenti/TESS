package com.tess.extensions.kotlin

fun String?.isNotNullOrEmpty() = !this.isNullOrEmpty()

fun String?.isNotNullOrBlank() = !this.isNullOrBlank()
