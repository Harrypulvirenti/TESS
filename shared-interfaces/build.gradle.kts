plugins {
    id(GradlePlugins.androidLibrary)
    id(GradlePlugins.kotlinAndroid)
    id(GradlePlugins.kotlinAndroidExtensions)
}

android {

    compileSdkVersion(AndroidSdk.compile)

    defaultConfig {

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

//    Coroutine
    implementation(Libraries.coroutinesCore)
    implementation(Libraries.coroutinesAndroid)
}
