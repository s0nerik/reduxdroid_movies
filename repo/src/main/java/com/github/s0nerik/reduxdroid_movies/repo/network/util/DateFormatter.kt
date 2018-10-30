package com.github.s0nerik.reduxdroid_movies.repo.network.util

import android.annotation.SuppressLint
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat

@SuppressLint("SimpleDateFormat")
private val MOVIEDB_DATE_FORMAT = DateTimeFormat.forPattern("yyyy-MM-dd")

internal fun formatMovieDbDate(date: DateTime) = MOVIEDB_DATE_FORMAT.print(date)
internal fun parseMovieDbDate(dateStr: String) = MOVIEDB_DATE_FORMAT.parseDateTime(dateStr)