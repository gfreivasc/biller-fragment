package com.gabrielfv.core.test.dummies

import android.os.Parcelable
import com.gabrielfv.core.arch.Controller
import com.gabrielfv.core.arch.View
import io.mockk.mockk
import kotlinx.android.parcel.Parcelize

class AController : Controller<A>() {
    override val view: View<A> get() = mockk()
    override val initialState = A()
}

@Parcelize
class A : Parcelable
