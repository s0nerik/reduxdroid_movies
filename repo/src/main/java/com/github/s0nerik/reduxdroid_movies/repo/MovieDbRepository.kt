package com.github.s0nerik.reduxdroid_movies.repo

import com.github.s0nerik.reduxdroid_movies.model.Movie
import com.github.s0nerik.reduxdroid_movies.repo.impl.LocalRepository
import com.github.s0nerik.reduxdroid_movies.repo.impl.NetworkRepository

interface MovieDbRepository {
    suspend fun getMovies(): List<Movie>
}

internal class MovieDbRepositoryImpl(
    private val localRepo: LocalRepository,
    private val networkRepo: NetworkRepository
) : MovieDbRepository {
    override suspend fun getMovies(): List<Movie> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}