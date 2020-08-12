plugins {
    libPlugin
}

android {
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(Deps.kotlinStdlib)
    implementation(Deps.androidCoreKtx)
    implementation(Deps.androidAppCompat)
    implementation(Deps.androidMaterial)

    implementation(Deps.androidViewBinding)
}