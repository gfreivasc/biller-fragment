plugins {
    libPlugin
}

dependencies {
    implementation(Deps.kotlinStdlib)
    implementation(Deps.androidCoreKtx)
    implementation(Deps.androidAppCompat)

    testImplementation(Deps.jUnit)
    androidTestImplementation(Deps.espresso)
    androidTestImplementation(Deps.espressoJUnit)
}