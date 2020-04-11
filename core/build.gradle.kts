import Modules.architectureSDK
import extensions.apiProject
import extensions.applyDefault

plugins {
    id(GradlePlugins.androidLibrary)
    id(GradlePlugins.kotlinAndroid)
    id(GradlePlugins.kotlinAndroidExtensions)
    id(GradlePlugins.kotlinKapt)
}


android {
    applyDefault()
}

repositories {
    maven(url = "https://kotlin.bintray.com/kotlinx")
}

dependencies {

    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(Libraries.kotlinStdlib)

    apiProject(architectureSDK)

//    Test
    testImplementation(TestLibraries.junit)
    testImplementation(TestLibraries.mockk)
    testImplementation(TestLibraries.kotlinTestArrow)
    testImplementation(TestLibraries.kotlinTestRunner)

    implementation(Libraries.firebaseFirestone)

//    Jackson
    implementation(Libraries.jackson)
    implementation(Libraries.jacksonAnnotation)

}
repositories {
    mavenCentral()
}
