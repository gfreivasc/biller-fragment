plugins {
    libPlugin
}

dependencies {
    implementation(project(":core-arch"))

    implementation(Deps.kotlinStdlib)
    implementation(Deps.androidCoreKtx)
    implementation(Deps.androidAppCompat)

    implementation(Deps.mockK)
    implementation(Deps.jUnit)
}