package plugins

import org.gradle.api.Plugin
import org.gradle.api.Project

class AndroidLibPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target.plugins) {
            apply("com.android.library")
            apply(AndroidBasePlugin::class.java)
        }
    }
}