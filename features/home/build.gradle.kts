plugins {
    libPlugin
    coreLibDesugar
    jacocoAndroid
}

android {
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(project(":core:core-arch"))
    implementation(project(":core:core-ktx"))
    implementation(project(":core:core-nav"))
    implementation(project(":common-resources"))
    implementation(project(":database"))

    implementation(Deps.kotlinStdlib)
    implementation(Deps.kotlinxDatetime)
    implementation(Deps.androidCoreKtx)
    implementation(Deps.androidAppCompat)
    implementation(Deps.androidMaterial)
    implementation(Deps.androidConstraint)

    implementation(Deps.jetpackNavUi)
    implementation(Deps.jetpackNavFragment)

    testImplementation(project(":core:core-test"))
    testImplementation(Deps.jUnit)
    testImplementation(Deps.mockK)
    androidTestImplementation(Deps.espresso)
    androidTestImplementation(Deps.espressoJUnit)
}
