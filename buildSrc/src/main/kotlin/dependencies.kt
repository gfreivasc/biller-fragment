object Versions {
    const val kotlin = "1.4.0-rc"
    const val androidGradlePlugin = "4.2.0-alpha07"

    const val androidCoreKtx = "1.3.1"
    const val androidSupport = "1.1.0"
    const val androidConstraint = "1.1.3"

    const val jetpackNav = "2.3.0"

    const val jUnit = "4.+"
    const val mockK = "1.10.0"
    const val espressoJUnit = "1.1.1"
    const val espresso = "3.2.0"
}

object Classpath {
    const val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
    const val androidGradlePlugin = "com.android.tools.build:gradle:${Versions.androidGradlePlugin}"
}

object Deps {
    const val kotlinStdlib = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin}"
    const val androidCoreKtx = "androidx.core:core-ktx:${Versions.androidCoreKtx}"
    const val androidAppCompat = "androidx.appcompat:appcompat:${Versions.androidSupport}"
    const val androidMaterial = "com.google.android.material:material:${Versions.androidSupport}"
    const val androidConstraint = "androidx.constraintlayout:constraintlayout:${Versions.androidConstraint}"

    const val jetpackNavFragment = "androidx.navigation:navigation-fragment-ktx:${Versions.jetpackNav}"
    const val jetpackNavUi = "androidx.navigation:navigation-ui-ktx:${Versions.jetpackNav}"

    const val jUnit = "junit:junit:${Versions.jUnit}"
    const val mockK = "io.mockk:mockk:${Versions.mockK}"
    const val espressoJUnit = "androidx.test.ext:junit:${Versions.espressoJUnit}"
    const val espresso = "androidx.test.espresso:espresso-core:${Versions.espresso}"
}