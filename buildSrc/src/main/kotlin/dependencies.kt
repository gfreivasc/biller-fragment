/*
 * Copyright 2020 Gabriel Freitas Vasconcelos
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
object Versions {
    const val kotlin = "1.4.0"
    const val kotlinxDatetime = "0.1.0"

    const val coreLibDesugar = "1.0.9"

    const val androidGradlePlugin = "4.2.0-alpha10"
    const val androidCoreKtx = "1.3.1"
    const val androidSupport = "1.1.0"
    const val androidConstraint = "1.1.3"
    const val androidRecyclerView = "1.1.0"

    const val jetpackNav = "2.3.0"

    const val coroutines = "1.3.0"

    const val roomDb = "2.2.5"

    const val jUnit = "4.+"
    const val mockK = "1.10.0"
    const val fragmentTesting = "1.2.4"
    const val espressoJUnit = "1.1.1"
    const val espresso = "3.2.0"
    const val jetpackTest = "1.3.0"

    const val jacoco = "0.8.5"
}

object Classpath {
    const val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
    const val androidGradlePlugin = "com.android.tools.build:gradle:${Versions.androidGradlePlugin}"
    const val jacocoCore = "org.jacoco:org.jacoco.core:${Versions.jacoco}"
}

object Deps {
    const val kotlinStdlib = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin}"
    const val kotlinxDatetime = "org.jetbrains.kotlinx:kotlinx-datetime:${Versions.kotlinxDatetime}"

    const val coreLibDesugar = "com.android.tools:desugar_jdk_libs:${Versions.coreLibDesugar}"

    const val androidCoreKtx = "androidx.core:core-ktx:${Versions.androidCoreKtx}"
    const val androidAppCompat = "androidx.appcompat:appcompat:${Versions.androidSupport}"
    const val androidMaterial = "com.google.android.material:material:${Versions.androidSupport}"
    const val androidConstraint = "androidx.constraintlayout:constraintlayout:${Versions.androidConstraint}"
    // That's probably a risky move
    const val androidViewBinding = "androidx.databinding:viewbinding:${Versions.androidGradlePlugin}"
    const val androidRecyclerView = "androidx.recyclerview:recyclerview:${Versions.androidRecyclerView}"

    const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"

    const val jetpackNavFragment = "androidx.navigation:navigation-fragment-ktx:${Versions.jetpackNav}"
    const val jetpackNavUi = "androidx.navigation:navigation-ui-ktx:${Versions.jetpackNav}"

    const val roomDb = "androidx.room:room-runtime:${Versions.roomDb}"
    const val roomDbKtx = "androidx.room:room-ktx:${Versions.roomDb}"
    const val roomDbCompiler = "androidx.room:room-compiler:${Versions.roomDb}"

    const val jUnit = "junit:junit:${Versions.jUnit}"
    const val mockK = "io.mockk:mockk:${Versions.mockK}"
    const val mockKAndroid = "io.mockk:mockk-android:${Versions.mockK}"
    const val espressoJUnit = "androidx.test.ext:junit:${Versions.espressoJUnit}"
    const val androidTestRunner = "androidx.test:runner:${Versions.jetpackTest}"
    const val androidOrchestrator = "androidx.test:orchestrator:${Versions.jetpackTest}"
    const val fragmentTesting = "androidx.fragment:fragment-testing:${Versions.fragmentTesting}"
    const val espresso = "androidx.test.espresso:espresso-core:${Versions.espresso}"
}
