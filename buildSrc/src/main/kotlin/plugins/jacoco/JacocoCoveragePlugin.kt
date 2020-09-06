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

import com.android.build.gradle.BaseExtension
import com.android.build.gradle.api.BaseVariant
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.file.FileTree
import org.gradle.api.logging.Logger
import org.gradle.api.logging.Logging.getLogger
import org.gradle.api.plugins.ExtensionAware
import org.gradle.api.tasks.testing.Test
import org.gradle.kotlin.dsl.*
import org.gradle.testing.jacoco.plugins.JacocoPluginExtension
import org.gradle.testing.jacoco.plugins.JacocoTaskExtension
import org.gradle.testing.jacoco.tasks.JacocoReport
import plugins.getAndroid
import plugins.getOrCreate
import plugins.variants

class JacocoCoveragePlugin : Plugin<Project> {
    companion object {
        const val EXTENSION_NAME = "jacocoCoverage"
        const val NO_VARIANT_TASK_NAME = "jacocoAndroidTestReport"
        const val BASE_TASK_NAME = "jacocoTestReport"
        const val TASK_GROUP = "Reporting"
    }

    private val logger: Logger = getLogger(javaClass)

    override fun apply(target: Project) {
        with(target.plugins) {
            apply("jacoco")
        }

        (target as ExtensionAware).extensions
                .findByType<JacocoPluginExtension>()
                ?.apply { toolVersion = Versions.jacoco }

        val extension = target.extensions
                .create<JacocoCoverageExtension>(
                    EXTENSION_NAME,
                    JacocoCoverageExtension.defaultExcludes
                )

        val baseTask = target.tasks
                .getOrCreate<JacocoReport>(BASE_TASK_NAME)
                .apply { group = TASK_GROUP }

        target.afterEvaluate {
            getAndroid<BaseExtension>().variants.forEach { variant ->
                val reportTask = variant
                        .createVariantReportTask(target, extension)
                baseTask.dependsOn(reportTask)
            }
        }
    }

    private fun BaseVariant.createVariantReportTask(
        project: Project,
        extension: JacocoCoverageExtension
    ): JacocoReport {
        val sourceDirs = sourceSets
                .flatMap { it.javaDirectories }
                .map { it.path }
        val testTask = project.tasks
                .getByName("test${name.capitalize()}UnitTest") as Test
        val execData = (testTask as ExtensionAware).extensions
                .getByType(JacocoTaskExtension::class)
                .destinationFile!!
                .path

        testTask.configure<JacocoTaskExtension> {
            isIncludeNoLocationClasses = extension.includesNoLocationClasses
        }

        return project.createReportTask(
            sourceDirs,
            listOf(execData),
            extension,
            testTask,
            this
        )
    }

    private fun Project.createReportTask(
        sourceDirs: List<String>,
        execData: List<String>,
        extension: JacocoCoverageExtension,
        testTask: Test,
        variant: BaseVariant? = null
    ): JacocoReport {
        val name = variant?.name?.let { name ->
            "jacoco${name.capitalize()}TestReport"
        } ?: NO_VARIANT_TASK_NAME

        return tasks.create<JacocoReport>(name) {
            group = TASK_GROUP
            dependsOn(testTask)

            reports {
                csv.isEnabled = extension.csv.isEnabled
                xml.isEnabled = extension.xml.isEnabled
                html.isEnabled = extension.html.isEnabled
            }

            sourceDirectories.setFrom(files(sourceDirs))
            executionData.setFrom(files(execData))
            classDirectories.setFrom(
                getClassTree(this@createReportTask, extension, variant)
            )
        }
    }

    private fun getClassTree(
        project: Project,
        extension: JacocoCoverageExtension,
        variant: BaseVariant? = null
    ): FileTree {
        val javaDir = variant?.let {
            variant.javaCompileProvider.get().destinationDir
        } ?: project.file("${project.buildDir}/intermediates/javac/debug")
        val javaTree = project.fileTree(
            "dir" to javaDir,
            "excludes" to extension.excludes
        )

        return if (project.plugins.hasPlugin("kotlin-android")) {
            val kotlinDir = variant?.name?.let { name ->
                "${project.buildDir}/tmp/kotlin-classes/$name"
            } ?: "${project.buildDir}/tmp/kotlin-classes/debug"

            val kotlinTree = project.fileTree(
                "dir" to kotlinDir,
                "excludes" to extension.excludes
            )
            javaTree + kotlinTree
        } else {
            javaTree
        }
    }
}
