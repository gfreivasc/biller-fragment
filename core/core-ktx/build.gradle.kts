plugins {
    libPlugin
    jacocoAndroid
}

dependencies {
    implementation(Deps.kotlinStdlib)
    implementation(Deps.androidCoreKtx)
    implementation(Deps.androidAppCompat)
    implementation(Deps.androidMaterial)

    implementation(Deps.androidViewBinding)
}
