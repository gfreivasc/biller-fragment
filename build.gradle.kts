// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    jacoco
}

jacoco {
    toolVersion = Versions.jacoco
}

buildscript {
    repositories {
        google()
        jcenter()
    }
    dependencies {
        classpath(Classpath.androidGradlePlugin)
        classpath(Classpath.kotlinGradlePlugin)
        classpath(Classpath.jacocoCore)
    }
}

allprojects {
    repositories {
        google()
        jcenter()
        maven(url = "https://kotlin.bintray.com/kotlinx/")
    }
}

tasks.register<Delete>("clean") {
    delete(rootProject.buildDir)
}

tasks.register<JacocoReport>("jacocoRootReport") {
    group = "Reporting"
    val tasks = subprojects
            .flatMap { it.tasks.withType<JacocoReport>() }
    dependsOn(tasks)

    tasks.forEach { report ->
        sourceDirectories.from(report.sourceDirectories)
        classDirectories.from(report.classDirectories)
    }

    executionData.setFrom(fileTree(rootDir.absolutePath) {
        include("**/build/jacoco/test*.exec")
    })

    reports {
        html.isEnabled = true
        xml.isEnabled = true
    }
}
