package extensions

import AndroidSdk
import com.android.build.gradle.TestedExtension
import java.io.File

fun TestedExtension.applyDefault(appId: String? = null) {

    applyBuildDefaultConfig(appId)
    applyBuildTypes()
    applyLint()
    applyTestOptions()
}

fun TestedExtension.applyBuildDefaultConfig(appId: String? = null) {

    compileSdkVersion(AndroidSdk.compile)

    defaultConfig {

        appId?.let { applicationId = appId }

        minSdkVersion(AndroidSdk.min)
        targetSdkVersion(AndroidSdk.target)
        versionCode = AndroidSdk.appVersionCode
        versionName = AndroidSdk.appVersionName
        testInstrumentationRunner = "android.support.test.runner.AndroidJUnitRunner"

    }
}

fun TestedExtension.applyBuildTypes() {

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }
}

fun TestedExtension.applyLint() {

    lintOptions {
        disable("InvalidPackage")
        baseline(File("lint-errors.xml"))
        isCheckAllWarnings = true
        isWarningsAsErrors = true
        isAbortOnError = true
    }
}

fun TestedExtension.applyTestOptions() {

    testOptions {
        unitTests.all {
            useJUnit()
        }
    }
}
