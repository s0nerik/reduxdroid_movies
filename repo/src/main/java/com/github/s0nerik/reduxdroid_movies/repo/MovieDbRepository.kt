package com.github.s0nerik.reduxdroid_movies.repo

import com.github.debop.kodatimes.months
import com.github.s0nerik.reduxdroid_movies.core.util.CoroutineContextHolder
import com.github.s0nerik.reduxdroid_movies.model.Movie
import com.github.s0nerik.reduxdroid_movies.repo.local.LocalRepository
import com.github.s0nerik.reduxdroid_movies.repo.network.NetworkRepository
import kotlinx.coroutines.coroutineScope
import org.joda.time.DateTime

interface MovieDbRepository {
    suspend fun getMovies(): List<Movie>
    suspend fun toggleFavorite(movie: Movie)
    suspend fun refresh(): List<Movie>
}

internal class MovieDbRepositoryImpl(
        private val localRepo: LocalRepository,
        private val networkRepo: NetworkRepository,
        ctx: CoroutineContextHolder
) : MovieDbRepository, CoroutineContextHolder by ctx {
    override suspend fun refresh(): List<Movie> = coroutineScope {
        localRepo.clear()
        getMovies()
    }

    override suspend fun getMovies(): List<Movie> = coroutineScope {
        var localMovies = localRepo.getMovies()

        if (localMovies.isEmpty()) {
            val to = DateTime.now()
            val from = to - 3.months()

            val networkMovies = networkRepo.getMovies(from, to)
            localRepo.replaceMovies(networkMovies)

            localMovies = localRepo.getMovies()
        }

        localMovies
    }

    override suspend fun toggleFavorite(movie: Movie) = localRepo.toggleFavorite(movie)
}