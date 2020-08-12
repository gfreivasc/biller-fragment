plugins {
    libPlugin
}

dependencies {
    implementation(Deps.kotlinStdlib)
    implementation(Deps.androidCoreKtx)
    implementation(Deps.androidAppCompat)
    implementation(Deps.androidMaterial)

    implementation(Deps.coroutines)

    implementation(Deps.androidViewBinding)
}