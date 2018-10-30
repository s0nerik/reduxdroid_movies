package com.github.s0nerik.reduxdroid_movies.repo

import com.github.debop.kodatimes.months
import com.github.s0nerik.reduxdroid_movies.model.Movie
import com.github.s0nerik.reduxdroid_movies.repo.local.LocalRepository
import com.github.s0nerik.reduxdroid_movies.repo.network.NetworkRepository
import org.joda.time.DateTime

interface MovieDbRepository {
    suspend fun getMovies(): List<Movie>
}

internal class MovieDbRepositoryImpl(
    private val localRepo: LocalRepository,
    private val networkRepo: NetworkRepository
) : MovieDbRepository {
    override suspend fun getMovies(): List<Movie> {
        val to = DateTime.now()
        val from = to - 3.months()

        return networkRepo.getMovies(from, to)
    }
}