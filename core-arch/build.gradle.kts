plugins {
    libPlugin
}

dependencies {
    implementation(Deps.kotlinStdlib)
    implementation(Deps.androidCoreKtx)
    implementation(Deps.androidAppCompat)

    testCompileOnly(Deps.jUnit)
    testCompileOnly(Deps.mockK)
    androidTestCompileOnly(Deps.espresso)
    androidTestCompileOnly(Deps.espressoJUnit)
    androidTestCompileOnly(Deps.fragmentTesting)
}