import Modules.core
import com.android.build.gradle.internal.dsl.TestOptions.UnitTestOptions
import extensions.implementationProject

plugins {
    id(GradlePlugins.androidApplication)
    id(GradlePlugins.kotlinAndroid)
    id(GradlePlugins.kotlinAndroidExtensions)
    id(GradlePlugins.kotlinKapt)
    id(GradlePlugins.fabric)
}

android {

    compileSdkVersion(AndroidSdk.compile)

    defaultConfig {

        applicationId = "com.tess.things"
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

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(Libraries.kotlinStdlib)

    implementationProject(core)

//    Test
    testImplementation(TestLibraries.junit)
    androidTestImplementation(TestLibraries.espresso)
    androidTestImplementation(TestLibraries.runner)
    testImplementation(TestLibraries.mockk)
    testImplementation(TestLibraries.kotlinTestArrow)
    testImplementation(TestLibraries.kotlinTestRunner)
    testImplementation(TestLibraries.coroutinesTest)

//    Things
    compileOnly(Libraries.things)

//    Room
    implementation(Libraries.room)
    kapt(Libraries.roomCompiler)
    implementation(Libraries.roomKTX)

//    Jackson
    implementation(Libraries.jacksonAnnotation)
}
apply(plugin = GradlePlugins.googleServices)
