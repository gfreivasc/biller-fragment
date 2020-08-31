package com.gabrielfv.core.nav

import androidx.collection.SimpleArrayMap

class CoreNavRegistry(
    override val routes: Routes
) : NavRegistry<Routes> {
    private val registry = SimpleArrayMap<String, Any>()

    override fun readAny(key: String): Any? = registry[key]
}
