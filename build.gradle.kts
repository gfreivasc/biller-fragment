// Top-level build file where you can add configuration options common to all sub-projects/modules.
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
