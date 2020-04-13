apply(plugin = "com.github.ben-manes.versions")

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
    apply(plugin = "org.jlleitschuh.gradle.ktlint")
    apply(plugin = "org.jlleitschuh.gradle.ktlint-idea")
}

tasks.register("clean", Delete::class) {
    delete(buildDir)
}

apply(plugin = "org.jlleitschuh.gradle.ktlint")
apply(plugin = "org.jlleitschuh.gradle.ktlint-idea")
