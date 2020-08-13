package com.gabrielfv.core.arch.coroutines

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class InstantCoroutinesExecutor : CoroutinesExecutor {

    override fun execute(command: suspend () -> Unit) {
        CoroutineScope(Dispatchers.Unconfined).launch {
            command()
        }
    }

    override fun destroy() = Unit
}