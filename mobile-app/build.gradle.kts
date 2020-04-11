import Modules.core
import Modules.mobileFeatures
import extensions.implementationProject

plugins {
    id(GradlePlugins.androidApplication)
    id(GradlePlugins.kotlinAndroid)
    id(GradlePlugins.kotlinAndroidExtensions)
    id(GradlePlugins.fabric)
}

android {

    compileSdkVersion(AndroidSdk.compile)

    defaultConfig {

        applicationId = "com.tess.mobile"
        minSdkVersion(AndroidSdk.min)
        targetSdkVersion(AndroidSdk.target)
        versionCode = AndroidSdk.appVersionCode
        versionName = AndroidSdk.appVersionName
        testInstrumentationRunner = "android.support.test.runner.AndroidJUnitRunner"

    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }

    lintOptions {
        disable("InvalidPackage")
        baseline(file("lint-errors.xml"))
        isCheckAllWarnings = true
        isWarningsAsErrors = true
        isAbortOnError = true
    }
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
