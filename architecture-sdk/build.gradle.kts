import Modules.extensionsAndroid
import Modules.extensionsKotlin
import Modules.sharedInterfaces
import extensions.apiProject

plugins {
    id(GradlePlugins.androidLibrary)
    id(GradlePlugins.kotlinAndroid)
    id(GradlePlugins.kotlinAndroidExtensions)
    id(GradlePlugins.kotlinKapt)
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

    apiProject(
        sharedInterfaces,
        extensionsAndroid,
        extensionsKotlin
    )

    testImplementation(TestLibraries.junit)

//    Logger
    implementation(Libraries.logger)

//    Arrow
    api(Libraries.arrowCoreData)
    api(Libraries.arrowCoreExt)

//    ViewModel
    api(Libraries.lifecycleViewModel)
    api(Libraries.lifecycleLiveData)
    api(Libraries.lifecycleRuntime)
    api(Libraries.lifecycleSavedState)
    kapt(Libraries.lifecycleCompiler)

//    Coroutine
    api(Libraries.coroutinesCore)
    api(Libraries.coroutinesAndroid)

//    Koin
    api(Libraries.koinAndroid)
    api(Libraries.koinScope)
    api(Libraries.koinViewModel)
    api(Libraries.koinFragment)
    api(Libraries.koinExperimental)

//    Firebase
    api(Libraries.firebaseCore)
    api(Libraries.firebaseCommon)

    //    Crashlytics
    api(Libraries.firebaseCrashlytics)
}
repositories {
    mavenCentral()
}
