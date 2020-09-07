plugins {
    libPlugin
    jacocoAndroid
}

dependencies {
    implementation(Deps.kotlinStdlib)
    implementation(Deps.androidCoreKtx)
    implementation(Deps.androidAppCompat)
    implementation(Deps.androidRecyclerView)

    implementation(Deps.jetpackNavFragment)

    implementation(Deps.coroutines)

    implementation(Deps.androidViewBinding)
}
