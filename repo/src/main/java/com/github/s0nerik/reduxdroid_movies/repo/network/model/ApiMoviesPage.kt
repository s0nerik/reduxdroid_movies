package com.github.s0nerik.reduxdroid_movies.repo.network.model

internal data class ApiMoviesPage(
    val page: Int,
    val totalResults: Int,
    val totalPages: Int,
    val results: List<ApiMovie>
)