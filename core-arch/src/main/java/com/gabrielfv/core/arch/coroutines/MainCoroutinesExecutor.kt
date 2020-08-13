package com.gabrielfv.core.arch.coroutines

import com.gabrielfv.core.arch.Destroyable
import kotlinx.coroutines.*

class MainCoroutinesExecutor : CoroutinesExecutor {
    private val scope: CoroutineScope by lazy {
        CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)
    }

    override fun execute(command: suspend () -> Unit) {
        scope.launch {
            command()
        }
    }

    override fun destroy() {
        scope.cancel()
    }
}