package extensions

import org.gradle.kotlin.dsl.PluginDependenciesSpecScope
import org.gradle.plugin.use.PluginDependenciesSpec

// fun PluginDependenciesSpecScope.kotlinLibrary(){
//     id(GradlePlugins.javaLibrary)
//     id(GradlePlugins.kotlin)
// }
//

fun PluginDependenciesSpecScope.androidLibraryExt(){
    id(GradlePlugins.androidLibrary)
    id(GradlePlugins.kotlinAndroid)
    id(GradlePlugins.kotlinAndroidExtensions)
}


fun PluginDependenciesSpec.kapt(){
    id(GradlePlugins.kotlinKapt)
}

// fun PluginDependenciesSpecScope.androidApplication(){
//     id(GradlePlugins.androidApplication)
//     id(GradlePlugins.kotlinAndroid)
//     id(GradlePlugins.kotlinAndroidExtensions)
// }