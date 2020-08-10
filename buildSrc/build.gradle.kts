plugins {
    kotlin("jvm") version "1.3.72"
    `kotlin-dsl`
}

repositories {
    google()
    jcenter()
    maven(url = "https://dl.bintray.com/kotlin/kotlin-eap")
}

dependencies {
    val agpVer = "4.2.0-alpha07"
    val kotlinVer = "1.4.0-rc"
    implementation(gradleApi())
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVer")
    implementation("org.jetbrains.kotlin:kotlin-stdlib:$kotlinVer")
    implementation("com.android.tools.build:gradle:$agpVer")
}

gradlePlugin {
    plugins {
        create("appPlugin") {
            id = "appPlugin"
            implementationClass = "plugins.AndroidAppPlugin"
        }
    }

    plugins {
        create("libPlugin") {
            id = "libPlugin"
            implementationClass = "plugins.AndroidLibPlugin"
        }
    }
}