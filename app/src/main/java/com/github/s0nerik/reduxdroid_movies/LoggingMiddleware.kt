package com.github.s0nerik.reduxdroid_movies

import com.github.s0nerik.reduxdroid.core.middleware.Middleware
import me.tatarka.redux.android.LogMiddleware

class LoggingMiddleware : Middleware<Any, Any> {
    private val log = LogMiddleware<Any, Any>("ACTION")

    override fun dispatch(next: (Any) -> Any, action: Any): Any = log.dispatch(next, action)
}