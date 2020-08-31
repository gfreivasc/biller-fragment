package com.gabrielfv.core.nav

object NavManager {
    private lateinit var registry: NavRegistry<*>

    fun <R> init(registry: NavRegistry<R>) {
        this.registry = registry
    }

    @Suppress("UNCHECKED_CAST")
    fun <R> getRegistry(): NavRegistry<R> = try {
        registry as NavRegistry<R>
    } catch (e: ClassCastException) {
        throw IllegalStateException("Requested registry with wrong routing")
    }
}