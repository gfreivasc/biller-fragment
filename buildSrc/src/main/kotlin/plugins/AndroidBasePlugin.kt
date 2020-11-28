/*
 * Copyright 2020 Gabriel Freitas Vasconcelos
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package plugins

import Android
import com.android.build.gradle.BaseExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.ExtensionAware
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

internal class AndroidBasePlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target.plugins) {
            apply("kotlin-android")
            apply("kotlin-parcelize")
        }

        target.getAndroid<BaseExtension>().apply {
            compileSdkVersion(Android.compileSdkVersion)
            buildToolsVersion(Android.buildToolsVersion)

            defaultConfig {
                minSdkVersion(Android.minSdkVersion)
                targetSdkVersion(Android.targetSdkVersion)

                testInstrumentationRunner(Android.testInstrumentationRunner)
            }

            testOptions {
                animationsDisabled = true
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
                    isTestCoverageEnabled = Android.testCoverageEnabled
                }
            }

            (this as ExtensionAware).configure<KotlinJvmOptions> {
                jvmTarget = "1.8"
            }
        }

        target.tasks.withType(KotlinCompile::class.java).forEach { task ->
            task.kotlinOptions.jvmTarget = "1.8"
        }
    }
}
