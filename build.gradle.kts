import extensions.android
import org.gradle.api.JavaVersion.VERSION_1_8
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

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

    gradle.projectsEvaluated {
        tasks.withType(KotlinCompile::class) {
            kotlinOptions.jvmTarget = VERSION_1_8.toString()
        }
    }
}
subprojects {
    apply(plugin = GradlePlugins.ktlint)
    apply(plugin = GradlePlugins.ktlintIdea)

    val androidPlugins = arrayOf(
        "com.android.build.gradle.AppPlugin",
        "com.android.build.gradle.LibraryPlugin"
    )

    project.plugins.whenPluginAdded(closureOf<Any> {
        if (this::class.qualifiedName in androidPlugins) {
            project.android {
                compileOptions {
                    sourceCompatibility = VERSION_1_8
                    targetCompatibility = VERSION_1_8
                }
            }
        }
    })
}

tasks.register("clean", Delete::class) {
    delete(buildDir)
}

apply(plugin = GradlePlugins.ktlint)
apply(plugin = GradlePlugins.ktlintIdea)
