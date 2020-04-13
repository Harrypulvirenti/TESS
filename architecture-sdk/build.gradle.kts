import Modules.sharedInterfaces
import extensions.applyDefault
import extensions.firebaseCommon
import extensions.implementationProject

plugins {
    id(GradlePlugins.androidLibrary)
    id(GradlePlugins.kotlinAndroid)
    id(GradlePlugins.kotlinAndroidExtensions)
}

android {
    applyDefault()
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(Libraries.kotlinStdlib)

    implementationProject(sharedInterfaces)

    // Logger
    implementation(Libraries.logger)

    // Koin
    implementation(Libraries.koinAndroid)

    // Firebase
    firebaseCommon()
    implementation(Libraries.firebaseCrashlytics)
}
repositories {
    mavenCentral()
}
