plugins {
    libPlugin
    `kotlin-kapt`
}

dependencies {
    implementation(Deps.kotlinStdlib)
    implementation(Deps.androidCoreKtx)

    implementation(Deps.roomDb)
    implementation(Deps.roomDbKtx)
    kapt(Deps.roomDbCompiler)
}