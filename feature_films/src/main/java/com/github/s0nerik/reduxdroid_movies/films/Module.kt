package com.github.s0nerik.reduxdroid_movies.films

import com.github.s0nerik.reduxdroid.core.di.AppModule
import com.github.s0nerik.reduxdroid.core.di.reducer
import com.github.s0nerik.reduxdroid.core.di.state
import com.github.s0nerik.reduxdroid_movies.core.util.ListDataState
import com.github.s0nerik.reduxdroid_movies.model.Movie
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import org.koin.androidx.viewmodel.ext.koin.viewModel

internal class Module : AppModule({
    viewModel { FilmsViewModel(get(), get(), get(), get(), get()) }
})