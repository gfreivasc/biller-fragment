package com.gabrielfv.core.test

import android.os.Bundle
import com.gabrielfv.core.arch.Controller

fun Controller<*>.start(
    savedState: Bundle? = null
) = onActivityCreated(savedState)
