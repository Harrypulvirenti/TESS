import extensions.applyAndroidDefault
import extensions.commonBaseDependencies
import extensions.featureBaseDependencies
import extensions.jackson

plugins {
    id(GradlePlugins.androidLibrary)
    id(GradlePlugins.kotlinAndroid)
    id(GradlePlugins.kotlinAndroidExtensions)
    id(GradlePlugins.kotlinKapt)
}

applyAndroidDefault()

repositories {
    maven(url = "https://kotlin.bintray.com/kotlinx")
}

dependencies {
    commonBaseDependencies()

    featureBaseDependencies()

    // Firebase
    implementation(Libraries.firebaseFirestone)

    // Jackson
    jackson()

}
repositories {
    mavenCentral()
}
