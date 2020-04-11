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
    implementation(Libraries.kotlinStdlib)

    implementationProject(core)
    implementationProject(mobileFeatures)

//    Test
    testImplementation(TestLibraries.junit)
    androidTestImplementation(TestLibraries.espresso)
    androidTestImplementation(TestLibraries.runner)

//    Support
    implementation(Libraries.appCompat)
    implementation(Libraries.constraintLayout)

}

apply(plugin = GradlePlugins.googleServices)
