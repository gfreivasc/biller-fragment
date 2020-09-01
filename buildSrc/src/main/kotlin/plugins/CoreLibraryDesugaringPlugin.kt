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

import com.android.build.gradle.BaseExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.ExtensionAware
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions

private const val DESUGARING_CONFIGURATION = "coreLibraryDesugaring"

class CoreLibraryDesugaringPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        val config = target.configurations.run {
            findByName(DESUGARING_CONFIGURATION)
                ?: create(DESUGARING_CONFIGURATION)
        }

        config.dependencies
                .add(target.dependencies.create(Deps.coreLibDesugar))

        target.getAndroid<BaseExtension>().apply {
            defaultConfig {
                multiDexEnabled = true
            }

            compileOptions {
                isCoreLibraryDesugaringEnabled = true

                sourceCompatibility(JavaVersion.VERSION_1_8)
                targetCompatibility(JavaVersion.VERSION_1_8)
            }

            (this as ExtensionAware).configure<KotlinJvmOptions> {
                jvmTarget = "1.8"
            }
        }
    }
}
