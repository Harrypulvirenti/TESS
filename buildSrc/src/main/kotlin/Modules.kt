object Modules {

    val all: Set<String>
        get() = mobileFeatures + setOf(
            mobileApp,
            thingsApp,
            sharedInterfaces,
            architectureSDK,
            core,
            extensionsAndroid,
            extensionsKotlin
        )

    val mobileFeatures: Set<String>
        get() = setOf(
            splashFeature,
            loginFeature
        )

    // App
    const val mobileApp = ":mobile-app"
    const val thingsApp = ":things-app"

    // Common
    const val sharedInterfaces = ":shared-interfaces"
    const val architectureSDK = ":architecture-sdk"
    const val core = ":core"
    const val extensionsAndroid = ":extensions-android"
    const val extensionsKotlin = ":extensions-kotlin"

    // Features
    const val splashFeature = ":splash-feature"
    const val loginFeature = ":login-feature"
}