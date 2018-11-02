package com.github.s0nerik.reduxdroid_movies.core_test

import com.github.s0nerik.reduxdroid_movies.core.util.CoroutineContextHolder
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

object TestCoroutineContextHolder : CoroutineContextHolder {
    override val main: CoroutineContext
        get() = Dispatchers.Default
    override val io: CoroutineContext
        get() = Dispatchers.Default
    override val bg: CoroutineContext
        get() = Dispatchers.Default
}
