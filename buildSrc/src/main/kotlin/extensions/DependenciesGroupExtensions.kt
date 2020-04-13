package extensions

import Libraries
import Modules
import TestLibraries
import org.gradle.kotlin.dsl.DependencyHandlerScope

fun DependencyHandlerScope.androidSupport() {
    implementation(Libraries.appCompat)
    implementation(Libraries.constraintLayout)
}

fun DependencyHandlerScope.arrow() {
    implementation(Libraries.arrowCoreData)
    implementation(Libraries.arrowCoreExt)
}

fun DependencyHandlerScope.lifecycle() {
    implementation(Libraries.lifecycleViewModel)
    implementation(Libraries.lifecycleLiveData)
    implementation(Libraries.lifecycleRuntime)
    implementation(Libraries.lifecycleSavedState)
    kapt(Libraries.lifecycleCompiler)
}

fun DependencyHandlerScope.coroutines() {
    implementation(Libraries.coroutinesCore)
    implementation(Libraries.coroutinesAndroid)
}

fun DependencyHandlerScope.koin() {
    implementation(Libraries.koinAndroid)
    implementation(Libraries.koinScope)
    implementation(Libraries.koinViewModel)
    implementation(Libraries.koinFragment)
    implementation(Libraries.koinExperimental)
}

fun DependencyHandlerScope.firebaseCommon() {
    implementation(Libraries.firebaseCore)
    implementation(Libraries.firebaseCommon)
}

fun DependencyHandlerScope.jackson() {
    implementation(Libraries.jackson)
    implementation(Libraries.jacksonAnnotation)
}

fun DependencyHandlerScope.room() {
    implementation(Libraries.room)
    kapt(Libraries.roomCompiler)
    implementation(Libraries.roomKTX)
}

fun DependencyHandlerScope.testingCommon() {
    testImplementation(TestLibraries.junit)
    testImplementation(TestLibraries.mockk)
    testImplementation(TestLibraries.kotlinTestArrow)
    testImplementation(TestLibraries.kotlinTestRunner)
}

fun DependencyHandlerScope.featureBaseDependencies() {
    implementation(Libraries.kotlinStdlib)

    implementationProject(
        Modules.sharedInterfaces,
        Modules.extensionsKotlin,
        Modules.extensionsAndroid,
        Modules.architectureSDK
    )
    androidSupport()
    arrow()
    lifecycle()
    coroutines()
    koin()
    firebaseCommon()
    testingCommon()
}