package com.github.s0nerik.reduxdroid_movies.films

import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import java.text.DateFormat
import java.util.*

@Serializable
data class FilmsHeaderItem(
    val title: String
)