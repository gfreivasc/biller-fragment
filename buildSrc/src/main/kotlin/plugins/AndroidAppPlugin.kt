package plugins

import Android
import App
import com.android.build.gradle.AppExtension
import org.gradle.api.Plugin
import org.gradle.api.Project

class AndroidAppPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target.plugins) {
            apply("com.android.application")
            apply(AndroidBasePlugin::class.java)
        }

        val android = target.extensions.findByType(AppExtension::class.java)
        android?.apply {
            defaultConfig {
                applicationId(App.applicationId)
                versionCode(App.versionCode)
                versionName(App.versionName)

                testInstrumentationRunner(Android.testInstrumentationRunner)
            }

            buildTypes {
                getByName("debug") {
                    applicationIdSuffix(App.debugSuffix)
                }
            }
        }
    }
}