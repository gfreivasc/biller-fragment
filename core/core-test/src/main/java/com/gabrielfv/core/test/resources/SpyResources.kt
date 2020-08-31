package com.gabrielfv.core.test.resources

import com.gabrielfv.core.arch.Controller
import io.mockk.spyk
import java.lang.IllegalArgumentException

inline fun <reified T : Any> spyingRes(
    real: T,
    noinline definitions: (ResourcesSpy.() -> Unit)? = null
): T {
    val spy = spyk(real)

    if (spy is Controller<*>) {
        val resourcesSpy = ResourcesSpy.forController(spy)
        definitions?.invoke(resourcesSpy)
    } else {
        throw IllegalArgumentException(
            "Class ${T::class.java.canonicalName} is not an expected resource handler."
        )
    }

    return spy
}
