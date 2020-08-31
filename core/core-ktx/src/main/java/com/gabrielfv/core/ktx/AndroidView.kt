package com.gabrielfv.core.ktx

import android.view.LayoutInflater
import android.view.View

val View.inflater: LayoutInflater get() = LayoutInflater.from(context)
