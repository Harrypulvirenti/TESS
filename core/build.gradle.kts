import extensions.applyDefault
import extensions.featureBaseDependencies
import extensions.jackson

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

    featureBaseDependencies()

    // Firebase
    implementation(Libraries.firebaseFirestone)

    // Jackson
    jackson()

}
repositories {
    mavenCentral()
}
