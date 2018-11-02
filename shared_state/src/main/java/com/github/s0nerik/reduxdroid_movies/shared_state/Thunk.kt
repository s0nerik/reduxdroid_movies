package com.github.s0nerik.reduxdroid_movies.shared_state

import android.content.Intent
import com.github.s0nerik.reduxdroid.core.Thunk
import com.github.s0nerik.reduxdroid_movies.core.middleware.IntentAction
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

fun CoroutineScope.refreshFilms(repo: MovieDbRepository): Thunk = { dispatch ->
    launch {
        try {
            dispatch(Refreshing.Start)
            val movies = repo.refresh()
            dispatch(Refreshing.Success(movies))
        } catch (t: Throwable) {
            dispatch(Refreshing.Error(t))
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

fun share(movie: Movie): Thunk = { dispatch ->
    val intent = Intent().apply {
        action = Intent.ACTION_SEND
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, "https://www.themoviedb.org/movie/${movie.id}")
    }

    dispatch(IntentAction(IntentAction.UseCase.START_ACTIVITY, intent))
}