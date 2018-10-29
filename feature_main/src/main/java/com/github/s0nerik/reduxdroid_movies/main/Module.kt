package com.github.s0nerik.reduxdroid_movies.main

import com.github.s0nerik.reduxdroid.core.di.AppModule
import com.github.s0nerik.reduxdroid.core.di.state
import com.github.s0nerik.reduxdroid_movies.model.Movie
import kotlinx.serialization.Serializable
import org.koin.androidx.viewmodel.ext.koin.viewModel

internal class Module : AppModule({
    state(MainState())

    viewModel { MainViewModel(get(), get(), get(), get()) }
}) {
    init {
        initGlideConfigs()
    }
}

// State

@Serializable
data class MainState internal constructor(
    val items: List<Movie> = emptyList()
)

// Actions



// Reducers