package com.github.s0nerik.reduxdroid_movies.repo

import com.github.s0nerik.reduxdroid.core.di.AppModule
import com.github.s0nerik.reduxdroid_movies.repo.impl.LocalRepository
import com.github.s0nerik.reduxdroid_movies.repo.impl.NetworkRepository

internal class Module : AppModule({
    single { LocalRepository() }
    single { NetworkRepository() }
    single { MovieDbRepositoryImpl(get(), get()) as MovieDbRepository }
})
