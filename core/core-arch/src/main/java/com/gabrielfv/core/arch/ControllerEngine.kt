package com.gabrielfv.core.arch

import android.os.Parcelable
import java.lang.IllegalStateException

internal class ControllerEngine<S : Parcelable>(
    override val view: View<S>,
) : ControllerDefinition<S> {
    private var _state: S? = null
        set(value) {
            if (value != null) {
                field = value
                view.updateState(value)
            }
        }
    override val state: S
        get() = try {
            _state!!
        } catch (ex: NullPointerException) {
            throw IllegalStateException("""
                Attempted to access ${this::class.java.simpleName} state prior to setting it.
                Make sure that your state is set before trying to access it.
            """.trimIndent())
        }

    override fun setState(state: S) {
        _state = state
    }

    private val destroyableSet = mutableSetOf<Destroyable>()

    override fun registerDestroyable(vararg destroyable: Destroyable) {
        destroyableSet.addAll(destroyable)
    }

    override fun destroy() {
        destroyableSet.forEach { it.destroy() }
        destroyableSet.clear()
    }
}
