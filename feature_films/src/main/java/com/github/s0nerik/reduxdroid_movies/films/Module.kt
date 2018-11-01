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
    state(FilmsState())

    reducer(::loadingStart)
    reducer(::loadingSuccess)
    reducer(::loadingError)
    reducer(::updateMoviesList)

    viewModel { FilmsViewModel(get(), get(), get(), get(), get()) }
})

// State

@Serializable
data class FilmsState internal constructor(
        internal val dataState: ListDataState<Movie> = ListDataState.create()
) {
    @Transient
    val items: List<Movie>
        get() = dataState.items

    @Transient
    val isLoading: Boolean
        get() = dataState.isLoading

    @Transient
    val loadingError: String?
        get() = dataState.loadingError
}

// Actions

internal sealed class Loading {
    object Start : Loading()

    @Serializable
    data class Success(val data: List<Movie>) : Loading()

    @Serializable
    data class Error(val error: Throwable) : Loading()
}

@Serializable
internal data class UpdateMoviesList(val data: List<Movie>)

// Reducers

internal fun loadingStart(a: Loading.Start, s: FilmsState) = s.copy(
        dataState = s.dataState.loading()
)

internal fun loadingSuccess(a: Loading.Success, s: FilmsState) = s.copy(
        dataState = s.dataState.successLoading(a.data)
)

internal fun loadingError(a: Loading.Error, s: FilmsState) = s.copy(
        dataState = s.dataState.errorLoading(a.error)
)

internal fun updateMoviesList(a: UpdateMoviesList, s: FilmsState) = s.copy(
        dataState = s.dataState.setItems(a.data)
)