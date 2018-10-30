package com.github.s0nerik.reduxdroid_movies.films

import com.github.s0nerik.reduxdroid_movies.model.Movie
import kotlinx.serialization.Serializable

@Serializable
data class FilmsItem(
    val movie: Movie
) {
    interface Listener {
        fun toggleFavorite(movie: Movie)
        fun share(movie: Movie)
    }
}