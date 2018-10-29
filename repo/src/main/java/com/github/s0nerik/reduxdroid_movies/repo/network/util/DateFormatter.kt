package com.github.s0nerik.reduxdroid_movies.repo.network.util

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat")
internal fun formatMovieDbDate(date: Date) = SimpleDateFormat("yyyy-mm-dd").format(date)