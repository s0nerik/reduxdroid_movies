package com.github.s0nerik.reduxdroid_movies.core.util

import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView

private const val RECYCLER_STATE = "__RECYCLER_STATE__"

fun RecyclerView?.saveState(outState: Bundle?) {
    if (outState == null) return

    this?.layoutManager?.onSaveInstanceState()?.let {
        outState.putParcelable(RECYCLER_STATE, it)
    }
}

fun RecyclerView?.restoreState(savedState: Bundle?) {
    if (savedState == null) return

    this?.layoutManager?.onSaveInstanceState()?.let {
        savedState.putParcelable(RECYCLER_STATE, it)
    }
}