plugins {
    libPlugin
    jacocoAndroid
}

android {
    defaultConfig {
        multiDexEnabled = true
    }
}

dependencies {
    implementation(project(":core:core-arch"))

    implementation(Deps.kotlinStdlib)
    implementation(Deps.androidCoreKtx)
    implementation(Deps.androidAppCompat)

    // objenesis versions > 2.6 cannot run on android projects with
    // minSdk < 26. MockK depends on it which breaks android tests,
    // When running tests globally, gradle tries to run this module's
    // (nonexistent) android tests, which causes the whole suite to break
    implementation(Deps.mockK) {
        exclude(module = "objenesis")
    }
    implementation("org.objenesis:objenesis:2.6")
    implementation(Deps.jUnit)
}
