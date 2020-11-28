plugins {
    kotlin("jvm") version "1.4.20"
    `kotlin-dsl`
}

repositories {
    google()
    jcenter()
}

dependencies {
    val agpVer = "4.2.0-alpha16"
    val kotlinVer = "1.4.20"
    val jacocoVer = "0.8.5"
    implementation(gradleApi())
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVer")
    implementation("org.jetbrains.kotlin:kotlin-stdlib:$kotlinVer")
    implementation("com.android.tools.build:gradle:$agpVer")
    implementation("org.jacoco:org.jacoco.core:$jacocoVer")
}

gradlePlugin {
    plugins {
        create("appPlugin") {
            id = "appPlugin"
            implementationClass = "plugins.AndroidAppPlugin"
        }

        create("libPlugin") {
            id = "libPlugin"
            implementationClass = "plugins.AndroidLibPlugin"
        }

        create("coreLibDesugar") {
            id = "coreLibDesugar"
            implementationClass = "plugins.CoreLibraryDesugaringPlugin"
        }

        create("jacocoAndroid") {
            id = "jacocoAndroid"
            implementationClass = "plugins.jacoco.JacocoCoveragePlugin"
        }

        create("androidOrchestrator") {
            id = "androidOrchestrator"
            implementationClass = "plugins.OrchestratorPlugin"
        }
    }
}
