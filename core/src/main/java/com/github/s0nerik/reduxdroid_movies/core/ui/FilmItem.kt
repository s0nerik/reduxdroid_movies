package com.github.s0nerik.reduxdroid_movies.core.ui

import com.github.s0nerik.reduxdroid_movies.model.Movie
import kotlinx.serialization.Serializable

@Serializable
data class FilmItem(
    val movie: Movie
) {
    interface Listener {
        fun toggleFavorite(item: FilmItem)
        fun share(item: FilmItem)
    }
}