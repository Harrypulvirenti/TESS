import Modules.core
import extensions.applyDefault
import extensions.featureBaseDependencies
import extensions.implementationProject
import extensions.room

plugins {
    id(GradlePlugins.androidApplication)
    id(GradlePlugins.kotlinAndroid)
    id(GradlePlugins.kotlinAndroidExtensions)
    id(GradlePlugins.kotlinKapt)
    id(GradlePlugins.fabric)
}

android {
    applyDefault(ApplicationId.things)
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    featureBaseDependencies()

    implementationProject(core)

    // Things
    compileOnly(Libraries.things)

    // Room
    room()

    // Jackson
    implementation(Libraries.jacksonAnnotation)

    // Test
    androidTestImplementation(TestLibraries.espresso)
    androidTestImplementation(TestLibraries.runner)
    testImplementation(TestLibraries.coroutinesTest)
}
apply(plugin = GradlePlugins.googleServices)
