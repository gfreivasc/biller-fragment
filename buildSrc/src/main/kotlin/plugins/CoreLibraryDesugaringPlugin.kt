package plugins

import com.android.build.gradle.BaseExtension
import org.gradle.api.Plugin
import org.gradle.api.Project

class CoreLibraryDesugaringPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        val dep = target.dependencies.add(
            "implementation",
            mapOf(
                "group" to "com.android.tools",
                "name" to "desugar_jdk_libs",
                "version" to Versions.androidJdkDesugar
            )
        )

        target.configurations.findByName("implementation")
            ?.dependencies
            ?.add(dep)

        target.getAndroid<BaseExtension>().apply {
            defaultConfig {
                multiDexEnabled = true
            }

            compileOptions {
                isCoreLibraryDesugaringEnabled = true
            }
        }
    }
}
