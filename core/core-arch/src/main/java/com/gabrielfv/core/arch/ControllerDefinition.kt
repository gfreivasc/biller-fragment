package com.gabrielfv.core.arch

import android.os.Parcelable

interface ControllerDefinition<S : Parcelable> : Destroyable {
    val view: View<S>
    val state: S

    fun setState(state: S)

    fun registerDestroyable(vararg destroyable: Destroyable)
}
