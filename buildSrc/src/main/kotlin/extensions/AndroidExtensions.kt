package extensions

import AndroidSdk
import com.android.build.gradle.BaseExtension
import org.gradle.api.Project

fun Project.applyAndroidDefault(appId: String? = null) {

    android {
        applyBuildDefaultConfig(appId)
        applyBuildTypes()
        applyLint(this@applyAndroidDefault)
        applyTestOptions()
    }
}

private fun BaseExtension.applyBuildDefaultConfig(appId: String? = null) {
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

private fun BaseExtension.applyBuildTypes() {
    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }
}

private fun BaseExtension.applyLint(project: Project) {
    lintOptions {
        disable("InvalidPackage")
        baseline(project.file("lint-errors.xml"))
        isCheckAllWarnings = true
        isWarningsAsErrors = true
        isAbortOnError = true
    }
}

private fun BaseExtension.applyTestOptions() {
    testOptions {
        unitTests.all {
            useJUnit()
        }
    }
}
