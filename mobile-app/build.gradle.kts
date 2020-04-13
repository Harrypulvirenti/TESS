import Modules.architectureSDK
import Modules.core
import Modules.mobileFeatures
import extensions.applyAndroidDefault
import extensions.commonBaseDependencies
import extensions.implementationProject

plugins {
    id(GradlePlugins.androidApplication)
    id(GradlePlugins.kotlinAndroid)
    id(GradlePlugins.kotlinAndroidExtensions)
    id(GradlePlugins.fabric)
}

applyAndroidDefault(ApplicationId.mobile)

dependencies {
    commonBaseDependencies()

    implementationProject(mobileFeatures + core + architectureSDK)

    // Koin
    implementation(Libraries.koinAndroid)
}

apply(plugin = GradlePlugins.googleServices)
