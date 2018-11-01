package com.github.s0nerik.reduxdroid_movies.shared_state

import com.github.s0nerik.reduxdroid.core.di.AppModule
import com.github.s0nerik.reduxdroid.core.di.reducer
import com.github.s0nerik.reduxdroid.core.di.state
import com.github.s0nerik.reduxdroid_movies.core.util.ListDataState
import com.github.s0nerik.reduxdroid_movies.model.Movie
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

internal class Module : AppModule({
    state(SharedState())

    reducer(::loadingStart)
    reducer(::loadingSuccess)
    reducer(::loadingError)
    reducer(::updateMoviesList)
})

// State

@Serializable
data class SharedState internal constructor(
        internal val filmsState: ListDataState<Movie> = ListDataState.create()
) {
    @Transient
    val films: List<Movie>
        get() = filmsState.items

    @Transient
    val isLoading: Boolean
        get() = filmsState.isLoading

    @Transient
    val loadingError: String?
        get() = filmsState.loadingError
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

internal fun loadingStart(a: Loading.Start, s: SharedState) = s.copy(
        filmsState = s.filmsState.loading()
)

internal fun loadingSuccess(a: Loading.Success, s: SharedState) = s.copy(
        filmsState = s.filmsState.successLoading(a.data)
)

internal fun loadingError(a: Loading.Error, s: SharedState) = s.copy(
        filmsState = s.filmsState.errorLoading(a.error)
)

internal fun updateMoviesList(a: UpdateMoviesList, s: SharedState) = s.copy(
        filmsState = s.filmsState.setItems(a.data)
)