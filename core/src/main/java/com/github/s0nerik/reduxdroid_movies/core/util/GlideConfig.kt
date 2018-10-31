package com.github.s0nerik.reduxdroid_movies.core.util

import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.github.s0nerik.glide_bindingadapter.GlideBindingConfig
import com.github.s0nerik.reduxdroid_movies.core.R

enum class GlideConfig {
    MOVIE_POSTER
}

internal fun initGlideConfigs() {
    GlideBindingConfig.registerProvider(GlideConfig.MOVIE_POSTER) { imageView, requestBuilder ->
        requestBuilder.apply(
                RequestOptions.centerCropTransform()
                        .placeholder(R.color.md_grey_300)
        ).transition(DrawableTransitionOptions().crossFade())
    }
}