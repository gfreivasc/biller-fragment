package com.gabrielfv.core.arch.coroutines

import com.gabrielfv.core.arch.Destroyable

interface CoroutinesExecutor : Destroyable {
    fun execute(command: suspend () -> Unit)
}