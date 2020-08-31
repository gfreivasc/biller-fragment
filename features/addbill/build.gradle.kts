plugins {
    libPlugin
}

android {
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(project(":core:core-arch"))
    implementation(project(":core:core-nav"))
    implementation(project(":database"))

    implementation(Deps.kotlinStdlib)
    implementation(Deps.androidCoreKtx)
    implementation(Deps.androidAppCompat)
    implementation(Deps.androidConstraint)
    implementation(Deps.androidMaterial)

    implementation(Deps.jetpackNavUi)
    implementation(Deps.jetpackNavFragment)

    testImplementation(project(":core:core-test"))
    testImplementation(Deps.jUnit)
    testImplementation(Deps.mockK)
}