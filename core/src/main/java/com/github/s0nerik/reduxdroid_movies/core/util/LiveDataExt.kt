package com.github.s0nerik.reduxdroid_movies.core.util

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Transformations

fun <S : Any, R : Any> LiveData<S>.map(mapper: (S) -> R): LiveData<R> =
        Transformations.map(this, mapper)

fun <S : Any, R : Any> LiveData<S?>.map(mapper: (S) -> R?, default: R): LiveData<R> =
        Transformations.map(this) { it?.let { mapper(it) } ?: default }

fun <S : Any, R : Any> LiveData<List<S>>.mapItems(mapper: (S) -> R): LiveData<List<R>> =
        map { list -> list.map { mapper(it) } }