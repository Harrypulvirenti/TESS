import extensions.applyAndroidDefault
import extensions.commonBaseDependencies
import extensions.coroutines

plugins {
    id(GradlePlugins.androidLibrary)
    id(GradlePlugins.kotlinAndroid)
    id(GradlePlugins.kotlinAndroidExtensions)
}

applyAndroidDefault()

dependencies {
    commonBaseDependencies()

    // Android
    implementation(Libraries.appCompat)
    implementation(Libraries.lifecycleViewModel)

    // Coroutine
    coroutines()
}
