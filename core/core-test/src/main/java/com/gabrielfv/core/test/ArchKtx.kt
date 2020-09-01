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
package com.gabrielfv.core.test

import android.os.Bundle
import android.os.Parcelable
import androidx.core.os.bundleOf
import com.gabrielfv.core.arch.Controller
import io.mockk.every
import io.mockk.mockk

fun Controller<*>.start(
    savedState: Bundle? = null
) = onActivityCreated(savedState)

fun mockParcelableBundleOf(vararg pairs: Pair<String, Parcelable>) =
    mockk<Bundle>(relaxed = true) {
        pairs.forEach { (key, value) ->
            every { getParcelable<Parcelable>(eq(key)) } returns value
        }
    }

fun stateBundle(state: Parcelable) = mockParcelableBundleOf(
    "com.gabrielfv.core.arch.STATE_REGISTRY" to state
)
