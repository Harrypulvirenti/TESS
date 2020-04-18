import Modules.architectureSDK
import Modules.core
import Modules.mobileFeatures
import extensions.applyAndroidDefault
import extensions.commonBaseDependencies
import extensions.implementationProject
import org.gradle.api.JavaVersion.VERSION_1_8

plugins {
    id(GradlePlugins.androidApplication)
    id(GradlePlugins.kotlinAndroid)
    id(GradlePlugins.kotlinAndroidExtensions)
    id(GradlePlugins.fabric)
}

android {
    applyAndroidDefault(ApplicationId.mobile)

    kotlinOptions {
        jvmTarget = VERSION_1_8.toString()
    }
}

dependencies {
    commonBaseDependencies()

    implementationProject(mobileFeatures + core + architectureSDK)

    // Koin
    implementation(Libraries.koinAndroid)
}

apply(plugin = GradlePlugins.googleServices)
