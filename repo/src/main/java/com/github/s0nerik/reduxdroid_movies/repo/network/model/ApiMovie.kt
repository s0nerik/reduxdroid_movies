package com.github.s0nerik.reduxdroid_movies.repo.network.model

import com.github.s0nerik.reduxdroid_movies.model.Movie
import com.github.s0nerik.reduxdroid_movies.repo.network.util.parseMovieDbDate
import org.joda.time.DateTime

internal data class ApiMovie(
    val id: Long,
    val voteCount: Int,
    val voteAverage: Float,
    val title: String?,
    val posterPath: String?,
    val overview: String?,
    val releaseDate: String?
) {
    fun toLocal() = Movie(
        id = id,
        name = title ?: "",
        description = overview ?: "",
        releaseDate = releaseDate?.let { parseMovieDbDate(it) } ?: DateTime(0),
        coverUrl = "https://image.tmdb.org/t/p/w500${posterPath}",
        isFavorite = false,
        rating = voteAverage
    )
}