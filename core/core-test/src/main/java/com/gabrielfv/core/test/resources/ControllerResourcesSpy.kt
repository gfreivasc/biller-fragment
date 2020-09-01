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
package com.gabrielfv.core.test.resources

import com.gabrielfv.core.arch.Controller
import io.mockk.every

internal class ControllerResourcesSpy(
    private val controller: Controller<*>
) : ResourcesSpy {

    init {
        initDefaults()
    }

    private fun initDefaults() {
        every { controller.getString(any()) } returns ""
        every { controller.getText(any()) } returns ""
    }

    override fun Int.toString(value: String) {
        every { controller.getString(eq(this@toString)) } returns value
    }
}
