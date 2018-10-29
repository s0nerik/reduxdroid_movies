package com.github.s0nerik.reduxdroid_movies.films

import com.github.s0nerik.reduxdroid.core.di.AppModule
import org.koin.androidx.viewmodel.ext.koin.viewModel

internal class Module : AppModule({
    viewModel { FilmsViewModel(get(), get(), get(), get()) }
})
