package com.hpdev.smartthermostatcore.viewmodel

import com.hpdev.smartthermostatcore.coroutine.CoroutineHandler
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch

interface SmartViewModel : CoroutineHandler

fun SmartViewModel.launchOnUIThread(action: () -> Unit) = launchOnThread(Main, action)

fun SmartViewModel.launchOnIOThread(action: () -> Unit) = launchOnThread(IO, action)

private fun SmartViewModel.launchOnThread(dispatcher: CoroutineDispatcher, action: () -> Unit) =
    launch(dispatcher) { action() }