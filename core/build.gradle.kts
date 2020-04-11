import Modules.architectureSDK
import com.android.build.gradle.internal.dsl.TestOptions.UnitTestOptions
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

    testOptions {
        unitTests(delegateClosureOf<UnitTestOptions> {
            all(
                KotlinClosure1<Any, Test>({
                    (this as Test).apply { useJUnit() }
                }, this)
            )
        })
    }
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
