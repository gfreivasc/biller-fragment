package plugins

import Android
import com.android.build.gradle.BaseExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

internal class AndroidBasePlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target.plugins) {
            apply("kotlin-android")
            apply("kotlin-android-extensions")
        }

        val android = target.extensions.findByType(BaseExtension::class.java)
        android?.apply {
            compileSdkVersion(Android.compileSdkVersion)
            buildToolsVersion(Android.buildToolsVersion)

            defaultConfig {
                minSdkVersion(Android.minSdkVersion)
                targetSdkVersion(Android.targetSdkVersion)

                testInstrumentationRunner(Android.testInstrumentationRunner)
            }

            compileOptions {
                sourceCompatibility(JavaVersion.VERSION_1_8)
                targetCompatibility(JavaVersion.VERSION_1_8)
            }

            buildTypes {
                getByName("release") {
                    minifyEnabled(Android.releaseMinify)
                    proguardFiles(
                        getDefaultProguardFile("proguard-android-optimize.txt"),
                        "proguard-rules.pro"
                    )
                }

                getByName("debug") {
                    minifyEnabled(Android.debugMinify)
                }
            }
        }

        target.tasks.withType(KotlinCompile::class.java).forEach { task ->
            task.kotlinOptions.jvmTarget = "1.8"
        }
    }
}