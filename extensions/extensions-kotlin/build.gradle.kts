plugins {
    id(GradlePlugins.javaLibrary)
    id(GradlePlugins.kotlin)
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(Libraries.kotlinStdlib)
}
