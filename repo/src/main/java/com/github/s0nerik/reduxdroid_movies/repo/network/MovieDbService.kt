package com.github.s0nerik.reduxdroid_movies.repo.network

import com.github.s0nerik.reduxdroid_movies.repo.network.model.ApiMoviesPage
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

internal interface MovieDbService {
    @GET("/discover/movie")
    fun getMoviesForPeriod(
        @Query("primary_release_date.gte") from: String,
        @Query("primary_release_date.lte") to: String,
        @Query("page") page: Int,
        @Query("region") region: String = "ua",
        @Query("include_video") includeVideo: Boolean = false,
        @Query("api_key") apiKey: String = "15279307ea57fc485f08df9da93f771b"
    ): Deferred<ApiMoviesPage>
}