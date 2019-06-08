package com.hpdev.architecture.sdk.extensions

inline fun <T> T.takeIfOrElse(predicate: (T) -> Boolean, orElse: () -> Unit): T? {
    return if (predicate(this)) {
        this
    } else {
        orElse()
        null
    }
}

fun <T> T?.or(or: T): T = this ?: or

fun <T1 : Any, T2 : Any, R : Any> onNotNull(first: T1?, second: T2?, action: (T1, T2) -> R): R? =
    if (first != null && second != null) action(first, second) else null

fun <T1 : Any, T2 : Any, T3 : Any, R : Any> onNotNull(
    first: T1?,
    second: T2?,
    third: T3?,
    action: (T1, T2, T3) -> R
): R? =
    if (first != null && second != null && third != null) action(first, second, third) else null