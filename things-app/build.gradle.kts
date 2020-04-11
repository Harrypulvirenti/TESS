import Modules.core
import extensions.applyDefault
import extensions.implementationProject

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
    implementation(Libraries.kotlinStdlib)

    implementationProject(core)

//    Test
    testImplementation(TestLibraries.junit)
    androidTestImplementation(TestLibraries.espresso)
    androidTestImplementation(TestLibraries.runner)
    testImplementation(TestLibraries.mockk)
    testImplementation(TestLibraries.kotlinTestArrow)
    testImplementation(TestLibraries.kotlinTestRunner)
    testImplementation(TestLibraries.coroutinesTest)

//    Things
    compileOnly(Libraries.things)

//    Room
    implementation(Libraries.room)
    kapt(Libraries.roomCompiler)
    implementation(Libraries.roomKTX)

//    Jackson
    implementation(Libraries.jacksonAnnotation)
}
apply(plugin = GradlePlugins.googleServices)
