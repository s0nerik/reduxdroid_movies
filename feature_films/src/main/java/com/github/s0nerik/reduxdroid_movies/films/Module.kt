package com.github.s0nerik.reduxdroid_movies.films

import com.github.s0nerik.reduxdroid.core.di.AppModule
import com.github.s0nerik.reduxdroid.core.di.reducer
import com.github.s0nerik.reduxdroid.core.di.state
import com.github.s0nerik.reduxdroid_movies.model.Movie
import kotlinx.serialization.Serializable
import org.koin.androidx.viewmodel.ext.koin.viewModel

internal class Module : AppModule({
    state(FilmsState())

    reducer(::loadingStart)
    reducer(::loadingSuccess)
    reducer(::loadingError)

    viewModel { FilmsViewModel(get(), get(), get(), get(), get()) }
})

// State

@Serializable
data class FilmsState internal constructor(
    val items: List<Movie> = emptyList(),
    val isLoading: Boolean = false,
    val loadingError: String? = null
)

// Actions

internal sealed class Loading {
    object Start : Loading()
    @Serializable data class Success(val data: List<Movie>) : Loading()
    @Serializable data class Error(val error: Throwable) : Loading()
}

// Reducers

internal fun loadingStart(a: Loading.Start, s: FilmsState) = s.copy(
    isLoading = true
)

internal fun loadingSuccess(a: Loading.Success, s: FilmsState) = s.copy(
    isLoading = false,
    loadingError = null,
    items = a.data
)

internal fun loadingError(a: Loading.Error, s: FilmsState) = s.copy(
    isLoading = false,
    loadingError = a.error.localizedMessage
)