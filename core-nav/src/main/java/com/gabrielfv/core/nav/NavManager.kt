package com.gabrielfv.core.nav

import androidx.collection.SimpleArrayMap
import java.lang.IllegalStateException

object NavManager {
    private lateinit var configuredRoutes: Routes
    val routes: Routes get() = if (::configuredRoutes.isInitialized) {
        configuredRoutes
    } else {
        throw IllegalStateException("NavigationManager has not been initialized with Routes")
    }

    fun init(routes: Routes) {
        configuredRoutes = routes
    }

    private val registry = SimpleArrayMap<String, Any>()

    fun readAny(key: String): Any? = registry[key]

    inline fun <reified T : Any> read(key: String): T? {
        return readAny(key) as? T
    }
}