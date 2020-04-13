import Modules.loginFeature
import extensions.applyAndroidDefault
import extensions.commonBaseDependencies
import extensions.featureBaseDependencies
import extensions.implementationProject

plugins {
    id(GradlePlugins.androidLibrary)
    id(GradlePlugins.kotlinAndroid)
    id(GradlePlugins.kotlinAndroidExtensions)
    id(GradlePlugins.kotlinKapt)
}

applyAndroidDefault()

dependencies {
    commonBaseDependencies()

    featureBaseDependencies()

    implementationProject(loginFeature)

    //    Test
    testImplementation(TestLibraries.junit)
    androidTestImplementation(TestLibraries.espresso)
    androidTestImplementation(TestLibraries.runner)
}
