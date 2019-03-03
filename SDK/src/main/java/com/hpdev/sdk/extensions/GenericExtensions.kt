package com.hpdev.sdk.extensions

inline fun <T> T.takeIfOrElse(predicate: (T) -> Boolean, orElse: () -> Unit): T? {
    return if (predicate(this)) {
        this
    } else {
        orElse()
        null
    }
}