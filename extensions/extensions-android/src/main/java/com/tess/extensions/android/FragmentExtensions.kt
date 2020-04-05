package com.tess.extensions.android

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment

fun Fragment.replaceChildFragment(
    @IdRes frameId: Int,
    fragment: Fragment,
    tag: String = fragment.javaClass.simpleName,
    addToStack: Boolean = false
) =
    childFragmentManager.inTransaction {
        replace(frameId, fragment, tag)
            .addToBackStack(addToStack, tag)
    }

@Suppress("UNCHECKED_CAST")
fun <T : Fragment> Fragment.findChildFragmentOrCreate(tag: String, fragmentCreator: () -> T): T =
    childFragmentManager.findFragmentByTag(tag) as? T ?: fragmentCreator()
