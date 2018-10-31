package com.github.s0nerik.reduxdroid_movies.model

import kotlinx.serialization.Serializable
import org.joda.time.DateTime

@Serializable
data class Movie(
    val id: Int,
    val name: String,
    val description: String,
    val releaseDate: DateTime,
    val coverUrl: String,
    val isFavorite: Boolean,
    val rating: Float
)