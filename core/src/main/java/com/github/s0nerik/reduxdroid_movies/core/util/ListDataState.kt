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
        NONE, LOADING, DATA, REFRESHING, EMPTY, ERROR
    }

    @Transient
    val items: List<T>
        get() = if (_state == State.DATA || _state == State.REFRESHING) _items else emptyList()

    @Transient
    val isEmpty: Boolean
        get() = _state == State.EMPTY

    @Transient
    val isLoading: Boolean
        get() = _state == State.LOADING

    @Transient
    val canRefresh: Boolean
        get() = _state != State.REFRESHING

    @Transient
    val isRefreshing: Boolean
        get() = _state == State.REFRESHING

    //region Loading
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
    //endregion

    //region Refreshing
    fun refreshing() = copy(
            _state = State.REFRESHING
    )

    fun successRefreshing(newItems: List<T>) = copy(
            _state = State.DATA,
            _items = newItems
    )

    fun errorRefreshing(t: Throwable) = copy(
            _state = State.ERROR,
            loadingError = t.localizedMessage
    )
    //endregion

    fun setItems(newItems: List<T>) = copy(
            _items = newItems
    )

    companion object {
        fun <T> create() = ListDataState<T>()
    }
}