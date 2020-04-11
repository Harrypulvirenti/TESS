object Libraries {

    // Kotlin
    const val kotlinStdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"

    // Coroutine
    const val coroutinesCore =
        "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"
    const val coroutinesAndroid =
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"

    // Arrow
    const val arrowCoreData = "io.arrow-kt:arrow-core-data:${Versions.arrow}"
    const val arrowCoreExt = "io.arrow-kt:arrow-core-extensions:${Versions.arrow}"

    // Support libs
    const val constraintLayout =
        "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
    const val appCompat = "androidx.appcompat:appcompat:${Versions.appCompat}"
    const val things = "com.google.android.things:androidthings:${Versions.things}"

    // Lifecycle
    const val lifecycleViewModel =
        "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}"
    const val lifecycleLiveData = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycle}"
    const val lifecycleRuntime = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycle}"
    const val lifecycleSavedState =
        "androidx.lifecycle:lifecycle-viewmodel-savedstate:${Versions.lifecycle}"
    const val lifecycleCompiler = "androidx.lifecycle:lifecycle-compiler:${Versions.lifecycle}"

    // Koin
    const val koinAndroid = "org.koin:koin-android:${Versions.koin}"
    const val koinScope = "org.koin:koin-androidx-scope:${Versions.koin}"
    const val koinViewModel = "org.koin:koin-androidx-viewmodel:${Versions.koin}"
    const val koinFragment = "org.koin:koin-androidx-fragment:${Versions.koin}"
    const val koinExperimental = "org.koin:koin-androidx-ext:${Versions.koin}"

    // Firebase
    const val firebaseCore = "com.google.firebase:firebase-core:${Versions.firebaseCore}"
    const val firebaseCommon = "com.google.firebase:firebase-common:${Versions.firebaseCommon}"
    const val firebaseCrashlytics =
        "com.crashlytics.sdk.android:crashlytics:${Versions.firebaseCrashlytics}"
    const val firebaseFirestone =
        "com.google.firebase:firebase-firestore:${Versions.firebaseFirestone}"

    // Room
    const val room = "androidx.room:room-runtime:${Versions.room}"
    const val roomCompiler = "androidx.room:room-compiler:${Versions.room}"
    const val roomKTX = "androidx.room:room-ktx:${Versions.room}"

    // Jackson
    const val jackson = "com.fasterxml.jackson.core:jackson-databind:${Versions.jackson}"
    const val jacksonAnnotation =
        "com.fasterxml.jackson.core:jackson-annotations:${Versions.jackson}"

    // Logger
    const val logger = "com.orhanobut:logger:${Versions.logger}"
}

object TestLibraries {

    const val junit = "androidx.test.ext:junit-ktx:${Versions.junit}"
    const val espresso = "androidx.test.espresso:espresso-core:${Versions.espresso}"
    const val runner = "androidx.test:runner:${Versions.runner}"
    const val mockk = "io.mockk:mockk:${Versions.mockk}"
    const val kotlinTestRunner = "io.kotlintest:kotlintest-runner-junit5:${Versions.kotlinTest}"
    const val kotlinTestArrow = "io.kotlintest:kotlintest-assertions-arrow:${Versions.kotlinTest}"
    const val robolectric = "org.robolectric:robolectric:${Versions.robolectric}"

    const val coroutinesTest =
        "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutines}"

    const val lifecycleTest = "ndroidx.arch.core:core-testing:${Versions.lifecycleTest}"

    const val koinTesting = "org.koin:koin-test:${Versions.koin}"
}