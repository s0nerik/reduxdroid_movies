package com.github.s0nerik.reduxdroid_movies.repo.local

import com.github.s0nerik.reduxdroid_movies.core.util.CoroutineContextHolder
import com.github.s0nerik.reduxdroid_movies.model.Movie
import com.github.s0nerik.reduxdroid_movies.repo.local.model.DbMovie
import io.objectbox.BoxStore
import io.objectbox.kotlin.boxFor
import kotlinx.coroutines.withContext

internal class LocalRepository(
        private val boxStore: BoxStore,
        ctx: CoroutineContextHolder
) : CoroutineContextHolder by ctx {
    private val moviesBox by lazy { boxStore.boxFor<DbMovie>() }

    suspend fun replaceMovies(movies: List<Movie>): Unit = withContext(io) {
        val oldMovies = moviesBox.all
        val newMovies = movies.map { DbMovie.fromLocal(it) }
        val mergedMovies = mergeMovies(oldMovies, newMovies)
        moviesBox.removeAll()
        moviesBox.put(mergedMovies)
    }

    /**
     * Merges isFavorite state from old movies
     *
     * NOTE: this method can mutate `newMovies` item contents
     */
    internal suspend fun mergeMovies(oldMovies: List<DbMovie>, newMovies: List<DbMovie>): List<DbMovie> = withContext(io) {
        val oldFavorites = mutableMapOf<Long, MutableList<DbMovie>>()
        oldMovies.asSequence()
                .filter { it.isFavorite }
                .groupByTo(oldFavorites) { it.id }

        val oldFavoriteIds = oldFavorites.keys

        val mergedMovies = mutableListOf<DbMovie>()
        newMovies.mapTo(mergedMovies) { movie ->
            if (movie.id in oldFavoriteIds) {
                movie.isFavorite = true
                oldFavorites -= movie.id
            }
            movie
        }
        mergedMovies += oldFavorites.values.map { it[0] }

        return@withContext mergedMovies
    }

    suspend fun getMovies(): List<Movie> = withContext(io) {
        moviesBox.all.map { it.toLocal() }
    }

    suspend fun toggleFavorite(movie: Movie): Unit = withContext(io) {
        val newMovie = movie.copy(isFavorite = !movie.isFavorite)
        moviesBox.put(DbMovie.fromLocal(newMovie))

        Unit
    }
}