package com.tess.extensions.android

import android.content.pm.PackageManager
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

fun AppCompatActivity.addFragment(
    @IdRes frameId: Int,
    fragment: Fragment,
    tag: String = fragment.javaClass.simpleName,
    addToStack: Boolean = false,
    hide: Boolean = false
) =
    supportFragmentManager.inTransaction {
        add(frameId, fragment, tag)
            .addToBackStack(addToStack, tag)
            .hide(hide, fragment)
    }

@Suppress("UNCHECKED_CAST")
fun <T : Fragment> AppCompatActivity.findFragmentOrCreate(
    tag: String,
    fragmentCreator: () -> T
): T =
    supportFragmentManager.findFragmentByTag(tag) as? T ?: fragmentCreator()

fun AppCompatActivity.swapActiveFragment(activeFragment: Fragment, hiddenFragment: Fragment) =
    supportFragmentManager.inTransaction {
        hide(activeFragment).show(hiddenFragment)
    }

fun AppCompatActivity.isPermissionsGranted(permissions: Array<String>): Boolean =
    permissions.all {
        ContextCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_GRANTED
    }

fun AppCompatActivity.askPermissions(permissions: Array<String>, requestCode: Int) =
    ActivityCompat.requestPermissions(this, permissions, requestCode)

inline fun AppCompatActivity.doIfPermissionsGrantedOr(
    permissions: Array<String>,
    ifGranted: () -> Unit,
    ifNotGranted: () -> Unit
) {
    if (isPermissionsGranted(permissions)) {
        ifGranted()
    } else {
        ifNotGranted()
    }
}
