plugins {
    libPlugin
}

android {
    buildFeatures {
        viewBinding = true
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(project(":core-arch"))
    implementation(project(":core-ktx"))
    implementation(project(":common-resources"))
    implementation(project(":database"))

    implementation(Deps.kotlinStdlib)
    implementation(Deps.androidCoreKtx)
    implementation(Deps.androidAppCompat)
    implementation(Deps.androidMaterial)
    implementation(Deps.androidConstraint)

    testImplementation(Deps.jUnit)
    testImplementation(Deps.mockK)
    androidTestImplementation(Deps.espresso)
    androidTestImplementation(Deps.espressoJUnit)
}