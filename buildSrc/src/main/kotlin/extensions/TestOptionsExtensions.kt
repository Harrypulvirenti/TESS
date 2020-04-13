package extensions

import com.android.build.gradle.internal.dsl.TestOptions
import groovy.lang.Closure
import org.gradle.api.tasks.testing.Test
import org.gradle.kotlin.dsl.closureOf

@Suppress("UNCHECKED_CAST")
fun TestOptions.UnitTestOptions.all(action: Test.() -> Unit) {
    all(closureOf(action) as Closure<Test>)
}