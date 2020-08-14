plugins {
    appPlugin
}

dependencies {
    implementation(project(":home"))
    implementation(project(":addbill"))
    implementation(project(":core-nav"))
    implementation(project(":database"))

    implementation(Deps.kotlinStdlib)
    implementation(Deps.androidCoreKtx)
    implementation(Deps.androidAppCompat)

    implementation(Deps.jetpackNavFragment)
    implementation(Deps.jetpackNavUi)

    testImplementation(Deps.jUnit)
    androidTestImplementation(Deps.espresso)
    androidTestImplementation(Deps.espressoJUnit)
}