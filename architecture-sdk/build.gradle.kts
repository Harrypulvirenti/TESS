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
