package com.gabrielfv.core.ktx

import android.view.LayoutInflater
import android.view.View

val View.inflater get() = LayoutInflater.from(context)
