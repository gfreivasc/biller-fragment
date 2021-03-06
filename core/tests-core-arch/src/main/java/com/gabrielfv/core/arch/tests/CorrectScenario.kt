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
package com.gabrielfv.core.arch.tests

import com.gabrielfv.core.arch.View
import com.gabrielfv.core.arch.extras.ViewProvider

class InitStateSetController(
    viewProvider: ViewProvider<InitStateSetController, CounterState>
) : CounterController() {
    override val view: View<CounterState> = viewProvider.get(this)

    override val initialState = CounterState(0)
}

class StateSettingInitializeController(
    viewProvider: ViewProvider<StateSettingInitializeController, CounterState>
) : CounterController() {
    override val view: View<CounterState> = viewProvider.get(this)

    override fun onInitialize(initialState: CounterState?) {
        setState(CounterState(0))
    }
}
