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
package com.gabrielfv.core.test.dummies

import android.os.Parcelable
import com.gabrielfv.core.arch.Controller
import com.gabrielfv.core.arch.View
import io.mockk.mockk
import kotlinx.parcelize.Parcelize

class AController : Controller<A>() {
    override val view: View<A> get() = mockk()
    override val initialState = A()
}

@Parcelize
class A : Parcelable
