package com.github.s0nerik.reduxdroid_movies.util

import com.github.s0nerik.reduxdroid.core.di.AppModule
import kotlinx.coroutines.Dispatchers

internal class Module : AppModule({
    single {
        CoroutineContextHolderImpl(
                main = Dispatchers.Main,
                io = Dispatchers.IO,
                bg = Dispatchers.Default
        ) as CoroutineContextHolder
    }
})
