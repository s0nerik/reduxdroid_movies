package com.github.s0nerik.reduxdroid_movies.repo.network

import com.github.s0nerik.reduxdroid_movies.model.Movie
import com.github.s0nerik.reduxdroid_movies.repo.network.model.ApiMoviesPage
import com.github.s0nerik.reduxdroid_movies.repo.network.util.formatMovieDbDate
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import java.util.*

internal class NetworkRepository(
    private val api: MovieDbService
) {
    suspend fun getMovies(from: Date, to: Date): List<Movie> = coroutineScope {
        val startDate = formatMovieDbDate(from)
        val endDate = formatMovieDbDate(to)

        val moviePages = mutableListOf<ApiMoviesPage>()

        val firstPage = api.getMoviesForPeriod(startDate, endDate, 1).await()
        moviePages += firstPage

        val remainingPages = firstPage.totalResults / firstPage.results.size - 1
        if (remainingPages > 0) {
            val deferreds = (2 until 2+remainingPages).map { page ->
                api.getMoviesForPeriod(startDate, endDate, page)
            }
            val pages = awaitAll(deferreds = *deferreds.toTypedArray())
            moviePages += pages
        }

        emptyList<Movie>()
    }
}