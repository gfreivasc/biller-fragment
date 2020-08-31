plugins {
    appPlugin
}

dependencies {
    implementation(project(":features:home"))
    implementation(project(":features:addbill"))
    implementation(project(":core:core-nav"))
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