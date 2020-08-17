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
