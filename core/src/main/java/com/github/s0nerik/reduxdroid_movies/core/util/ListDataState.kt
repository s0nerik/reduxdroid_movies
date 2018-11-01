package com.github.s0nerik.reduxdroid_movies.core.util

import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
data class ListDataState<T> internal constructor(
        private val _state: State = State.NONE,
        private val _items: List<T> = emptyList(),
        val loadingError: String? = null
) {
    internal enum class State {
        NONE, LOADING, DATA, EMPTY, ERROR
    }

    @Transient
    val items: List<T>
        get() = if (_state == State.DATA) _items else emptyList()

    @Transient
    val isEmpty: Boolean
        get() = _state == State.EMPTY

    @Transient
    val isLoading: Boolean
        get() = _state == State.LOADING

    @Transient
    val canRefresh: Boolean
        get() = _state != State.LOADING

    fun loading() = copy(
            _state = State.LOADING
    )

    fun successLoading(newItems: List<T>) = copy(
            _state = State.DATA,
            _items = newItems
    )

    fun errorLoading(t: Throwable) = copy(
            _state = State.ERROR,
            loadingError = t.localizedMessage
    )

    fun setItems(newItems: List<T>) = copy(
            _items = newItems
    )

    companion object {
        fun <T> create() = ListDataState<T>()
    }
}