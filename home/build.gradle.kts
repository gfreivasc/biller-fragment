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

    implementation(Deps.kotlinStdlib)
    implementation(Deps.androidCoreKtx)
    implementation(Deps.androidAppCompat)
    implementation(Deps.androidMaterial)
    implementation(Deps.androidConstraint)

    testImplementation(Deps.jUnit)
    androidTestImplementation(Deps.espresso)
    androidTestImplementation(Deps.espressoJUnit)
}