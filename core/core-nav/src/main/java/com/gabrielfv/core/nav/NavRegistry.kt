package com.gabrielfv.core.nav

interface NavRegistry<R> {
    val routes: R

    fun readAny(key: String): Any?

    companion object {
        const val BILLS_MODIFIED = "BILLS_MODIFIED"
    }
}

inline fun <reified T : Any> NavRegistry<*>.read(key: String): T? {
    return readAny(key) as? T
}
