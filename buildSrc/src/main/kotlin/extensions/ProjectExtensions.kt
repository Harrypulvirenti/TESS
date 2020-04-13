package extensions

import com.android.build.gradle.BaseExtension
import org.gradle.api.Project

internal fun Project.android(config: BaseExtension.() -> Unit) {
    val android = extensions.findByName("android") as? BaseExtension
        ?: error("Project '$name' is not an Android module")

    config(android)
}