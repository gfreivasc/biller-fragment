package com.gabrielfv.core.test.resources

import com.gabrielfv.core.arch.Controller
import io.mockk.spyk
import java.lang.IllegalArgumentException

inline fun <reified T : Any> spyingRes(real: T, definitions: ResourcesSpy.() -> Unit): T {
    val spy = spyk(real)

    if (spy is Controller<*>) {
        definitions(ResourcesSpy.forController(spy))
    } else {
        throw IllegalArgumentException(
            "Class ${T::class.java.canonicalName} is not an expected resource handler."
        )
    }

    return spy
}
