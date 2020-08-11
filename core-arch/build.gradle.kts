plugins {
    libPlugin
}

dependencies {
    implementation(Deps.kotlinStdlib)
    implementation(Deps.androidCoreKtx)
    implementation(Deps.androidAppCompat)

    testImplementation(Deps.jUnit)
    testImplementation(Deps.mockK)
    androidTestImplementation(Deps.espresso)
    androidTestImplementation(Deps.espressoJUnit)
}