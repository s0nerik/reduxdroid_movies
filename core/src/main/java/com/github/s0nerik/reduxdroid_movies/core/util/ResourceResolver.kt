package com.github.s0nerik.reduxdroid_movies.core.util

import android.app.Application
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import org.jetbrains.anko.dip
import org.jetbrains.anko.sp

interface ResourceResolver {
    fun str(@StringRes id: Int, vararg formatArgs: Any): String
    fun str(resName: String, vararg formatArgs: Any): String
    fun getColor(@ColorRes id: Int): Int
    fun dip(value: Int): Int
    fun sp(value: Int): Int
}

internal class ResourceResolverImpl(
        private val app: Application
) : ResourceResolver {
    override fun str(id: Int, vararg formatArgs: Any) = app.getString(id, *formatArgs)
    override fun str(resName: String, vararg formatArgs: Any): String {
        val id = app.resources.getIdentifier(resName, "string", app.packageName)
        if (id <= 0)
            error("String ${resName} not found")
        return app.getString(id, *formatArgs)
    }
    override fun getColor(id: Int) = ContextCompat.getColor(app, id)
    override fun dip(value: Int) = app.dip(value)
    override fun sp(value: Int) = app.sp(value)
}