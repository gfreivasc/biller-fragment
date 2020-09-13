plugins {
    libPlugin
    jacocoAndroid
}

dependencies {
    implementation(Deps.kotlinStdlib)
    implementation(Deps.kotlinxDatetime)
    implementation(Deps.androidCoreKtx)
    implementation(Deps.androidAppCompat)
    implementation(Deps.androidMaterial)

    implementation(Deps.androidViewBinding)

    testImplementation(Deps.jUnit)
    testImplementation(Deps.mockK)
}
