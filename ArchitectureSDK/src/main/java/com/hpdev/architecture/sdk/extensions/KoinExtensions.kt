package com.hpdev.architecture.sdk.extensions

import android.app.Application
import org.koin.android.ext.android.getKoin
import org.koin.core.definition.Kind
import kotlin.reflect.full.isSubclassOf

internal inline fun <reified T : Any> Application.getAllOfType(): Collection<T> =
    getKoin().let { koin ->
        koin.beanRegistry
            .getAllDefinitions()
            .filter { it.isKind(Kind.Single) }
            .filter { it.primaryType.isSubclassOf(T::class) }
            .map { koin.get<T>(clazz = it.primaryType, qualifier = null, parameters = null) }
    }