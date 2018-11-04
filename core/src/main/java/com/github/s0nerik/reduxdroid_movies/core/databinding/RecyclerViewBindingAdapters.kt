package com.github.s0nerik.reduxdroid_movies.core.databinding

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.github.s0nerik.reduxdroid_movies.core.R

@BindingAdapter("runLayoutAnimation")
fun runLayoutAnimation(recycler: RecyclerView, run: Boolean) {
    if (run) {
        recycler.post { recycler.scheduleLayoutAnimation() }
        recycler.adapter?.notifyDataSetChanged()
    }
}

@BindingAdapter("runLayoutAnimationOnce")
fun runLayoutAnimationOnce(recycler: RecyclerView, run: Boolean) {
    val layoutAnimationOccured = recycler.getTag(R.id.core_runLayoutAnimationOnce_occurred_tag_id) as? Boolean
    if (run && layoutAnimationOccured != true) {
        recycler.post { recycler.scheduleLayoutAnimation() }
        recycler.adapter?.notifyDataSetChanged()
        recycler.setTag(R.id.core_runLayoutAnimationOnce_occurred_tag_id, true)
    }
}