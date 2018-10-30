package com.github.s0nerik.reduxdroid_movies.films

import com.github.s0nerik.reduxdroid.core.Thunk
import com.github.s0nerik.reduxdroid_movies.repo.MovieDbRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

internal fun CoroutineScope.loadFilms(repo: MovieDbRepository): Thunk = { dispatch ->
    launch {
        try {
            dispatch(Loading.Start)
            val movies = repo.getMovies()
            dispatch(Loading.Success(movies))
        } catch (t: Throwable) {
            dispatch(Loading.Error(t))
        }
    }
}