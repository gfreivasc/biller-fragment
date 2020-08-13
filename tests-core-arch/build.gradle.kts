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
    implementation(project(":common-resources"))

    implementation(Deps.kotlinStdlib)
    implementation(Deps.androidAppCompat)
    implementation(Deps.androidConstraint)
    implementation(Deps.androidMaterial)

    testImplementation(Deps.jUnit)
    testImplementation(Deps.mockK)
    androidTestImplementation(Deps.espresso)
    androidTestImplementation(Deps.espressoJUnit)
    androidTestImplementation(Deps.fragmentTesting)
}