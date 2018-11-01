package com.github.s0nerik.reduxdroid_movies.favorites

import com.github.s0nerik.reduxdroid_movies.shared_state.SharedState

internal val SharedState.favorites
    get() = films.filter { it.isFavorite }

internal val SharedState.isFavoritesEmpty
    get() = !isLoading && favorites.isEmpty()