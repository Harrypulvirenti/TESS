import Modules.core
import extensions.applyAndroidDefault
import extensions.commonBaseDependencies
import extensions.featureBaseDependencies
import extensions.implementationProject
import extensions.room
import org.gradle.api.JavaVersion.VERSION_1_8

plugins {
    id(GradlePlugins.androidApplication)
    id(GradlePlugins.kotlinAndroid)
    id(GradlePlugins.kotlinAndroidExtensions)
    id(GradlePlugins.kotlinKapt)
    id(GradlePlugins.fabric)
}

android {
    applyAndroidDefault(ApplicationId.things)

    kotlinOptions {
        jvmTarget = VERSION_1_8.toString()
    }
}

dependencies {
    commonBaseDependencies()

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
