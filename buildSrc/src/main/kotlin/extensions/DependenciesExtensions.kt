package extensions

import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.kotlin.dsl.project

fun DependencyHandler.implementationProject(vararg module: String) =
    implementationProject(module.asIterable())

fun DependencyHandler.implementationProject(modules: Iterable<String>) {
    modules.forEach {
        add("implementation", project(it))
    }
}

fun DependencyHandler.apiProject(vararg module: String) =
    apiProject(module.asIterable())

fun DependencyHandler.apiProject(modules: Iterable<String>) {
    modules.forEach {
        add("api", project(it))
    }
}