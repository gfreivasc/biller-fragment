package com.gabrielfv.core.test.resources

import com.gabrielfv.core.arch.Controller
import io.mockk.every

internal class ControllerResourcesSpy(
    private val controller: Controller<*>
) : ResourcesSpy {

    override fun Int.toString(value: String) {
        every { controller.getString(this@toString) } returns value
    }
}