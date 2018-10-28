package com.github.s0nerik.reduxdroid_movies.model

import kotlinx.serialization.Serializable

@Serializable
data class SampleModel(
        val id: String,
        val data: String,
        val detailsText: String
)