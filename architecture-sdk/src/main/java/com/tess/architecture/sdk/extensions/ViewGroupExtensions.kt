package com.tess.architecture.sdk.extensions

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes

fun ViewGroup.inflateLayout(@LayoutRes id: Int, viewGroup: ViewGroup = this, attachToRoot: Boolean = true) {
    LayoutInflater.from(context).inflate(id, viewGroup, attachToRoot)
}

fun ViewGroup.makeGone() {
    visibility = ViewGroup.GONE
}

fun ViewGroup.makeVisible() {
    visibility = ViewGroup.VISIBLE
}

fun ViewGroup.makeInvisible() {
    visibility = ViewGroup.INVISIBLE
}

fun ViewGroup.isGone(): Boolean {
    return visibility == ViewGroup.GONE
}

fun ViewGroup.isVisible(): Boolean {
    return visibility == ViewGroup.VISIBLE
}

fun ViewGroup.isInvisible(): Boolean {
    return visibility == ViewGroup.INVISIBLE
}
