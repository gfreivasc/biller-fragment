package com.gabrielfv.core.test

import org.hamcrest.CoreMatchers
import org.hamcrest.Matcher

inline fun <reified T : Any> isA(): Matcher<Any> =
    CoreMatchers.instanceOf(T::class.java)