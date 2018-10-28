package com.github.s0nerik.reduxdroid_movies.core.util

import androidx.recyclerview.widget.DiffUtil

inline fun <T : Any> diffCallback(
        crossinline areItemsTheSame: (old: T, new: T) -> Boolean,
        crossinline areContentsTheSame: (old: T, new: T) -> Boolean
): DiffUtil.ItemCallback<T> = object : DiffUtil.ItemCallback<T>() {
    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean = areItemsTheSame(oldItem, newItem)
    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean = areContentsTheSame(oldItem, newItem)
}