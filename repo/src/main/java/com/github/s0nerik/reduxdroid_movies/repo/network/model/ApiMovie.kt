package com.github.s0nerik.reduxdroid_movies.repo.network.model

import java.util.*

internal data class ApiMovie(
    val id: Int,
    val voteCount: Int,
    val voteAverage: Float,
    val title: String?,
    val posterPath: String?,
    val overview: String?,
    val releaseDate: Date?
)