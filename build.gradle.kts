apply(plugin = GradlePlugins.versions)

buildscript {

    repositories {
        google()
        jcenter()

        maven(url = "https://maven.fabric.io/public")
        maven(url = "https://plugins.gradle.org/m2/")
    }
    dependencies {
        classpath(Classpath.androidBuildTools)
        classpath(Classpath.kotlin)
        classpath(Classpath.googleServices)
        classpath(Classpath.fabricTool)
        classpath(Classpath.ktlint)
        classpath(Classpath.versions)
    }
}

allprojects {
    repositories {
        google()
        jcenter()
        maven(url = "https://maven.google.com/")
    }
}
subprojects {
    apply(plugin = GradlePlugins.ktlint)
    apply(plugin = GradlePlugins.ktlintIdea)
}

tasks.register("clean", Delete::class) {
    delete(buildDir)
}

apply(plugin = GradlePlugins.ktlint)
apply(plugin = GradlePlugins.ktlintIdea)
