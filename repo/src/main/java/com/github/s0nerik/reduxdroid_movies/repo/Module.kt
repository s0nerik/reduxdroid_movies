package com.github.s0nerik.reduxdroid_movies.repo

import com.github.s0nerik.reduxdroid.core.di.AppModule
import com.github.s0nerik.reduxdroid_movies.repo.local.LocalRepository
import com.github.s0nerik.reduxdroid_movies.repo.network.MovieDbService
import com.github.s0nerik.reduxdroid_movies.repo.network.NetworkRepository
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

internal class Module : AppModule({
    single { LocalRepository() }
    single { NetworkRepository(get()) }
    single { MovieDbRepositoryImpl(get(), get()) as MovieDbRepository }

    single(name = "MovieDb") {
        GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create()
    }

    single(name = "MovieDb") {
        OkHttpClient.Builder().build()
    }

    single {
        Retrofit.Builder()
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create(get(name = "MovieDb")))
            .client(get(name = "MovieDb"))
            .baseUrl("https://api.themoviedb.org/3")
            .build()
            .create(MovieDbService::class.java)
    }
})
