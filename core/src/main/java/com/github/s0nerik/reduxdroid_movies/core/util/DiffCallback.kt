package com.github.s0nerik.reduxdroid_movies.core.util

import androidx.recyclerview.widget.DiffUtil

/**
 * Calls respective callbacks for each item.
 *
 * @return `DiffUtil.ItemCallback<T>`
 */
inline fun <T : Any> diffCallback(
    crossinline areItemsTheSame: (old: T, new: T) -> Boolean,
    crossinline areContentsTheSame: (old: T, new: T) -> Boolean
): DiffUtil.ItemCallback<T> = object : DiffUtil.ItemCallback<T>() {
    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean = areItemsTheSame(oldItem, newItem)
    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean = areContentsTheSame(oldItem, newItem)
}

/**
 * Checks a type of each item and if types are the same - calls respective callbacks.
 * Otherwise, items are compared using "==".
 *
 * @return `DiffUtil.ItemCallback<Any>`
 */
inline fun <reified T : Any> genericDiffCallback(
    crossinline areItemsTheSame: (old: T, new: T) -> Boolean,
    crossinline areContentsTheSame: (old: T, new: T) -> Boolean = { old, new -> old == new }
): DiffUtil.ItemCallback<Any> = object : DiffUtil.ItemCallback<Any>() {
    override fun areItemsTheSame(old: Any, new: Any): Boolean =
        if (old is T && new is T) {
            areItemsTheSame(old, new)
        } else {
            old == new
        }

    override fun areContentsTheSame(old: Any, new: Any): Boolean =
        if (old is T && new is T) {
            areContentsTheSame(old, new)
        } else {
            old == new
        }
}