package com.gabrielfv.core.test.resources

import com.gabrielfv.core.arch.Controller

interface ResourcesSpy {
    infix fun Int.toString(value: String)

    companion object {
        fun forController(spy: Controller<*>): ResourcesSpy =
            ControllerResourcesSpy(spy)
    }
}