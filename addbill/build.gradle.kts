plugins {
    libPlugin
}

android {
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(project(":core-arch"))
    implementation(project(":core-nav"))
    implementation(project(":database"))

    implementation(Deps.kotlinStdlib)
    implementation(Deps.androidCoreKtx)
    implementation(Deps.androidAppCompat)
    implementation(Deps.androidConstraint)
    implementation(Deps.androidMaterial)

    implementation(Deps.jetpackNavUi)
    implementation(Deps.jetpackNavFragment)

    testImplementation(Deps.jUnit)
}