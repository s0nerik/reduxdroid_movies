package com.github.s0nerik.reduxdroid_movies.shared_state

import com.github.s0nerik.reduxdroid.core.Thunk
import com.github.s0nerik.reduxdroid_movies.model.Movie
import com.github.s0nerik.reduxdroid_movies.repo.MovieDbRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

fun CoroutineScope.loadFilms(repo: MovieDbRepository): Thunk = { dispatch ->
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

fun CoroutineScope.toggleFavorite(repo: MovieDbRepository, movie: Movie): Thunk = { dispatch ->
    launch {
        repo.toggleFavorite(movie)
        val movies = repo.getMovies()
        dispatch(UpdateMoviesList(movies))
    }
}