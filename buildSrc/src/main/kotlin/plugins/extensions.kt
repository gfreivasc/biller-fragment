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
package plugins

import com.android.build.gradle.AppExtension
import com.android.build.gradle.BaseExtension
import com.android.build.gradle.LibraryExtension
import com.android.build.gradle.api.BaseVariant
import org.gradle.api.DomainObjectSet
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.tasks.TaskContainer

inline fun <reified Ex : BaseExtension> Project.getAndroid(): Ex =
    extensions.findByType(Ex::class.java)
        ?: throw IllegalArgumentException(
            "Getting android extension on non-android module"
        )

inline fun <reified T : Task> TaskContainer.getOrCreate(name: String): T {
    return findByName(name) as? T ?: create(name, T::class.java)
}

val BaseExtension.variants: DomainObjectSet<out BaseVariant> get() =
    if (this is LibraryExtension) {
        libraryVariants
    } else {
        (this as AppExtension).applicationVariants
    }
