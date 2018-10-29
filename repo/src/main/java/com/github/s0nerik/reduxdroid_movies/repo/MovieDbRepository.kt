package com.github.s0nerik.reduxdroid_movies.repo

import com.github.s0nerik.reduxdroid_movies.model.Movie
import com.github.s0nerik.reduxdroid_movies.repo.local.LocalRepository
import com.github.s0nerik.reduxdroid_movies.repo.network.NetworkRepository

interface MovieDbRepository {
    suspend fun getMovies(): List<Movie>
}

internal class MovieDbRepositoryImpl(
    private val localRepo: LocalRepository,
    private val networkRepo: NetworkRepository
) : MovieDbRepository {
    override suspend fun getMovies(): List<Movie> {
        return emptyList()
    }
}