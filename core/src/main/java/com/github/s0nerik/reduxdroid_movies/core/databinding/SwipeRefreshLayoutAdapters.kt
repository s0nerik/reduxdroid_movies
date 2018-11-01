package com.github.s0nerik.reduxdroid_movies.core.databinding

import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

@BindingAdapter("onRefresh")
fun onRefresh(swipeRefreshLayout: SwipeRefreshLayout, listener: SwipeRefreshLayout.OnRefreshListener) {
    swipeRefreshLayout.setOnRefreshListener {
        listener.onRefresh()
    }
}

@BindingAdapter("isRefreshing")
fun setRefreshing(swipeRefreshLayout: SwipeRefreshLayout, refreshing: Boolean) {
    swipeRefreshLayout.isRefreshing = refreshing
}