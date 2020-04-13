package extensions

import AndroidSdk
import org.gradle.api.Project

fun Project.applyAndroidDefault(appId: String? = null) {

    applyBuildDefaultConfig(appId)
    applyBuildTypes()
    applyLint()
    applyTestOptions()
}

fun Project.applyBuildDefaultConfig(appId: String? = null) {

    android {
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
}

fun Project.applyBuildTypes() {

    android {
        buildTypes {
            release {
                isMinifyEnabled = false
                proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
            }
        }
    }
}

fun Project.applyLint() {

    android {
        lintOptions {
            disable("InvalidPackage")
            baseline(file("lint-errors.xml"))
            isCheckAllWarnings = true
            isWarningsAsErrors = true
            isAbortOnError = true
        }
    }
}

fun Project.applyTestOptions() {

    android {
        testOptions {
            unitTests.all {
                useJUnit()
            }
        }
    }
}
