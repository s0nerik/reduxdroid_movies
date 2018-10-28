package com.github.s0nerik.reduxdroid_movies.model

import kotlinx.serialization.Serializable

@Serializable
data class Movie(
        val name: String,
        val year: Int,
        val coverUrl: String,
        val isFavorite: Boolean
)