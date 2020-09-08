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
import org.gradle.api.Plugin
import org.gradle.api.Project

class OrchestratorPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        target.dependency(
            "androidTestImplementation",
            Deps.androidTestRunner
        )
        target.dependency(
            "androidTestUtil",
            Deps.androidOrchestrator
        )
        target.getAndroid<BaseExtension>().apply {
            defaultConfig {
                // This option currently clears coverage data
//                testInstrumentationRunnerArguments["clearPackageData"] = "true"
            }
            testOptions {
                execution = "ANDROIDX_TEST_ORCHESTRATOR"
            }
        }
    }
}
