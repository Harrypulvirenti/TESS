import Modules.sharedInterfaces
import extensions.applyAndroidDefault
import extensions.commonBaseDependencies
import extensions.firebaseCommon
import extensions.implementationProject

plugins {
    id(GradlePlugins.androidLibrary)
    id(GradlePlugins.kotlinAndroid)
    id(GradlePlugins.kotlinAndroidExtensions)
}

applyAndroidDefault()

dependencies {
    commonBaseDependencies()

    implementationProject(sharedInterfaces)

    // Support
    implementation(Libraries.appCompat)

    // Logger
    implementation(Libraries.logger)

    // Koin
    implementation(Libraries.koinAndroid)
    implementation(Libraries.koinFragment)

    // Firebase
    firebaseCommon()
    implementation(Libraries.firebaseCrashlytics)
}
repositories {
    mavenCentral()
}
