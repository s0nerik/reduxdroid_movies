package com.github.s0nerik.reduxdroid_movies.core.util

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Transformations

fun <S : Any, R : Any> LiveData<S>.map(mapper: (S) -> R): LiveData<R> =
        Transformations.map(this, mapper)

fun <S : Any, R : Any> LiveData<S?>.map(mapper: (S) -> R?, default: R): LiveData<R> =
        Transformations.map(this) { it?.let { mapper(it) } ?: default }

fun <T> LiveData<T>.distinctUntilChanged(): LiveData<T> {
    val mediator: MediatorLiveData<T> = MediatorLiveData()
    var latestValue : T? = null
    mediator.addSource(this) {
        if (latestValue != it) {
            mediator.value = it
            latestValue = it
        }
    }
    return mediator
}