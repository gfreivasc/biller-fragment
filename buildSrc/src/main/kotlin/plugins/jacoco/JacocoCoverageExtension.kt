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
package plugins.jacoco

abstract class JacocoCoverageExtension(
    var excludes: Collection<String>
) {
    val csv: ReportConfiguration = ReportConfiguration(false)
    val xml: ReportConfiguration = ReportConfiguration(true)
    val html: ReportConfiguration = ReportConfiguration(true)

    var includesNoLocationClasses = true

    companion object {
        val androidDataBindingExcludes = listOf(
            "android/databinding/**/*.class",
            "**/android/databinding/*Binding.class",
            "**/BR.*",
            "**/databinding/*Binding.class"
        )

        val androidExcludes = listOf(
            "**/R.class",
            "**/R$*.class",
            "**/BuildConfig.*",
            "**/Manifest*.*"
        )

        val dagger2Excludes = listOf(
            "**/*_MembersInjector.class",
            "**/Dagger*Component.class",
            "**/Dagger*Component\$Builder.class",
            "**/*Module_*Factory.class"
        )

        val defaultExcludes get() = androidDataBindingExcludes +
                androidExcludes +
                dagger2Excludes
    }
}
