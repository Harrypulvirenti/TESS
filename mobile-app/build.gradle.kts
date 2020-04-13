import Modules.architectureSDK
import Modules.core
import Modules.mobileFeatures
import extensions.applyDefault
import extensions.implementationProject

plugins {
    id(GradlePlugins.androidApplication)
    id(GradlePlugins.kotlinAndroid)
    id(GradlePlugins.kotlinAndroidExtensions)
    id(GradlePlugins.fabric)
}

android {
    applyDefault(ApplicationId.mobile)
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    implementationProject(mobileFeatures + core + architectureSDK)

    // Koin
    implementation(Libraries.koinAndroid)
}

apply(plugin = GradlePlugins.googleServices)
