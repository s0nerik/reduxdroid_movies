package com.github.s0nerik.reduxdroid_movies.repo.local

import com.github.s0nerik.reduxdroid_movies.core.util.CoroutineContextHolder
import com.github.s0nerik.reduxdroid_movies.model.Movie
import com.github.s0nerik.reduxdroid_movies.repo.local.model.DbMovie
import io.objectbox.BoxStore
import io.objectbox.kotlin.boxFor
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext

internal class LocalRepository(
        private val boxStore: BoxStore,
        ctx: CoroutineContextHolder
) : CoroutineContextHolder by ctx {
    private val moviesBox by lazy { boxStore.boxFor<DbMovie>() }

    suspend fun clear(): Unit = coroutineScope {
        withContext(io) {
            moviesBox.removeAll()
        }
    }

    suspend fun replaceMovies(movies: List<Movie>): Unit = coroutineScope {
        clear()
        withContext(io) {
            moviesBox.put(movies.map { DbMovie.fromLocal(it) })
        }
    }

    suspend fun getMovies(): List<Movie> = coroutineScope {
        withContext(io) {
            moviesBox.all.map { it.toLocal() }
        }
    }

    suspend fun toggleFavorite(movie: Movie): Unit = coroutineScope {
        withContext(io) {
            val newMovie = movie.copy(isFavorite = !movie.isFavorite)
            moviesBox.put(DbMovie.fromLocal(newMovie))
        }
        Unit
    }
}