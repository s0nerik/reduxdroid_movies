package com.github.s0nerik.reduxdroid_movies.core

import com.github.s0nerik.reduxdroid.core.di.AppModule
import com.github.s0nerik.reduxdroid_movies.core.util.*
import kotlinx.coroutines.Dispatchers

internal class Module : AppModule({
    single { ResourceResolverImpl(get()) as ResourceResolver }

    single {
        CoroutineContextHolderImpl(
                main = Dispatchers.Main,
                io = Dispatchers.IO,
                bg = Dispatchers.Default
        ) as CoroutineContextHolder
    }
}) {
    init {
        initGlideConfigs()
    }
}