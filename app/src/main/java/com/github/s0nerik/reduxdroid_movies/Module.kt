package com.github.s0nerik.reduxdroid_movies

import com.github.s0nerik.reduxdroid.activity_result.ActivityResultMiddleware
import com.github.s0nerik.reduxdroid.core.di.AppModule
import com.github.s0nerik.reduxdroid.core.di.combinedReducer
import com.github.s0nerik.reduxdroid.core.di.middlewares
import com.github.s0nerik.reduxdroid.navigation.di.navForward
import com.github.s0nerik.reduxdroid.navigation.middleware.NavigationMiddleware
import me.tatarka.redux.Reducer

internal class Module : AppModule({
    combinedReducer() bind Reducer::class

    middlewares {
        listOf(
                get<ActivityResultMiddleware>(),
                get<NavigationMiddleware>()
        )
    }
})