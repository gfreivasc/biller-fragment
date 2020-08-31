package com.gabrielfv.core.arch.extras

import android.os.Parcelable
import com.gabrielfv.core.arch.Controller
import com.gabrielfv.core.arch.View

fun interface ViewProvider<C : Controller<T>, T : Parcelable> {
    fun get(controller: C): View<T>
}