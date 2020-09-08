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

import Versions
import com.android.build.gradle.BaseExtension
import com.android.build.gradle.api.BaseVariant
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.file.FileCollection
import org.gradle.api.file.FileTree
import org.gradle.api.plugins.ExtensionAware
import org.gradle.api.tasks.testing.Test
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.findByType
import org.gradle.testing.jacoco.plugins.JacocoPluginExtension
import org.gradle.testing.jacoco.plugins.JacocoTaskExtension
import org.gradle.testing.jacoco.tasks.JacocoReport
import plugins.getAndroid
import plugins.getOrCreate
import plugins.variants

class JacocoCoveragePlugin : Plugin<Project> {
    companion object {
        const val EXTENSION_NAME = "jacocoCoverage"
        const val BASE_TASK_NAME = "jacocoTestReport"
        const val TASK_GROUP = "Reporting"
    }

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
                // for some reason reporting both debug and release variants
                // is making reports collide upon aggregation. Perhaps modify
                // this to unify reporting and verify if collision does not
                // happen.
                if (variant.name != "debug") {
                    val reportTask = variant
                            .createVariantReportTask(target, extension)
                    baseTask.dependsOn(reportTask)
                }
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
        val execData = project.getExecData(name)

        testTask.configure<JacocoTaskExtension> {
            isIncludeNoLocationClasses = true
            excludes = listOf("jdk.internal.*")
        }

        return project.createReportTask(
            project.files(sourceDirs),
            execData,
            extension,
            testTask,
            this
        )
    }

    private fun Project.createReportTask(
        sourceDirs: FileCollection,
        execData: FileCollection,
        extension: JacocoCoverageExtension,
        testTask: Test,
        variant: BaseVariant
    ): JacocoReport {
        return tasks.create<JacocoReport>(
            "jacoco${variant.name.capitalize()}TestReport"
        ) {
            group = TASK_GROUP
            dependsOn(testTask)

            reports {
                csv.isEnabled = extension.csv.isEnabled
                xml.isEnabled = extension.xml.isEnabled
                html.isEnabled = extension.html.isEnabled
            }

            sourceDirectories.setFrom(sourceDirs)
            executionData.setFrom(execData)
            classDirectories.setFrom(
                getClassTree(this@createReportTask, extension, variant)
            )
        }
    }

    private fun getClassTree(
        project: Project,
        extension: JacocoCoverageExtension,
        variant: BaseVariant
    ): FileTree {
        val javaDir = variant.javaCompileProvider.get().destinationDir
        val javaTree = project.fileTree(javaDir) {
            exclude(extension.excludes)
        }

        return if (project.plugins.hasPlugin("kotlin-android")) {
            val kotlinDir = variant.name.let { name ->
                "${project.buildDir}/tmp/kotlin-classes/$name"
            }
            val kotlinTree = project.fileTree(kotlinDir) {
                exclude(extension.excludes)
            }
            javaTree + kotlinTree
        } else {
            javaTree
        }
    }

    private fun Project.getExecData(
        variantName: String
    ): FileCollection {
        return fileTree(buildDir) {
            include(
                "outputs/code_coverage/*AndroidTest/connected/**/*.ec",
                "jacoco/test${variantName.capitalize()}UnitTest.exec"
            )
        }
    }
}
