plugins {
    libPlugin
    coreLibDesugar
    `kotlin-kapt`
}

dependencies {
    implementation(Deps.kotlinStdlib)
    implementation(Deps.androidCoreKtx)
    implementation(Deps.kotlinxDatetime)

    implementation(Deps.roomDb)
    implementation(Deps.roomDbKtx)
    kapt(Deps.roomDbCompiler)

    testImplementation(Deps.jUnit)
}
