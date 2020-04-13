package com.tess.extensions.android

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

inline fun FragmentManager.inTransaction(transaction: FragmentTransaction.() -> FragmentTransaction) {
    beginTransaction().transaction().commit()
}

fun FragmentTransaction.hide(perform: Boolean, fragment: Fragment): FragmentTransaction =
    if (perform) hide(fragment) else this

fun FragmentTransaction.addToBackStack(perform: Boolean, tag: String): FragmentTransaction =
    if (perform) addToBackStack(tag) else this
