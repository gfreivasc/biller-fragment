package com.gabrielfv.core.arch

import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

interface View<T : Parcelable> {
    val controller: Controller<T>

    fun inflate(inflater: LayoutInflater, root: ViewGroup?): View

    fun updateState(state: T)
}