package com.github.s0nerik.reduxdroid_movies.repo

import android.app.Application
import com.github.s0nerik.reduxdroid.core.di.AppModule
import com.github.s0nerik.reduxdroid_movies.repo.local.LocalRepository
import com.github.s0nerik.reduxdroid_movies.repo.local.model.MyObjectBox
import com.github.s0nerik.reduxdroid_movies.repo.network.MovieDbService
import com.github.s0nerik.reduxdroid_movies.repo.network.NetworkRepository
import com.github.simonpercic.oklog3.OkLogInterceptor
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import io.objectbox.BoxStore
import io.objectbox.android.AndroidObjectBrowser
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

internal class Module : AppModule({
    single { LocalRepository(get(), get()) }
    single { NetworkRepository(get(), get()) }
    single { MovieDbRepositoryImpl(get(), get(), get()) as MovieDbRepository }

    single(name = "MovieDb") {
        GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create()
    }

    single(name = "MovieDb") {
        OkHttpClient.Builder()
            .addInterceptor(
                OkLogInterceptor.builder()
                    .withRequestHeaders(true)
                    .withResponseHeaders(true)
                    .build()
            )
            .build()
    }

    single {
        Retrofit.Builder()
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create(get(name = "MovieDb")))
            .client(get(name = "MovieDb"))
            .baseUrl("https://api.themoviedb.org/3/")
            .build()
            .create(MovieDbService::class.java)
    }

    single {
        val boxStore = MyObjectBox.builder().androidContext(get<Application>()).build()
        if (BuildConfig.DEBUG) {
            AndroidObjectBrowser(boxStore).start(get<Application>())
        }
        boxStore as BoxStore
    }
})
