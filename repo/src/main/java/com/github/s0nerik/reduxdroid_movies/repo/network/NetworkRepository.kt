package com.github.s0nerik.reduxdroid_movies.repo.network

import com.github.s0nerik.reduxdroid_movies.model.Movie
import com.github.s0nerik.reduxdroid_movies.repo.network.model.ApiMoviesPage
import com.github.s0nerik.reduxdroid_movies.repo.network.util.formatMovieDbDate
import com.github.s0nerik.reduxdroid_movies.util.CoroutineContextHolder
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext
import org.joda.time.DateTime

internal class NetworkRepository(
        private val api: MovieDbService,
        ctx: CoroutineContextHolder
) : CoroutineContextHolder by ctx {
    suspend fun getMovies(from: DateTime, to: DateTime): List<Movie> = withContext(io) {
        val startDate = formatMovieDbDate(from)
        val endDate = formatMovieDbDate(to)

        val moviePages = mutableListOf<ApiMoviesPage>()

        val firstPage = api.getMoviesForPeriod(startDate, endDate, 1).await()
        moviePages += firstPage

        val remainingPages = firstPage.totalPages - 1
        if (remainingPages > 0) {
            val deferreds = (2 until 2 + remainingPages).map { page ->
                api.getMoviesForPeriod(startDate, endDate, page)
            }
            val pages = awaitAll(deferreds = *deferreds.toTypedArray())
            moviePages += pages
        }

        val movies = moviePages.flatMap { it.results }.map { it.toLocal() }

        return@withContext movies
    }
}