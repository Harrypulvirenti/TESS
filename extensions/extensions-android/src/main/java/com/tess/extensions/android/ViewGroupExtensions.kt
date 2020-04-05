package com.tess.extensions.android

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes

fun ViewGroup.inflateLayout(@LayoutRes id: Int, viewGroup: ViewGroup = this, attachToRoot: Boolean = true) {
    LayoutInflater.from(context).inflate(id, viewGroup, attachToRoot)
}
