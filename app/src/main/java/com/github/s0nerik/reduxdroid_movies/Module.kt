package com.github.s0nerik.reduxdroid_movies

import com.github.s0nerik.reduxdroid.activity_result.ActivityResultMiddleware
import com.github.s0nerik.reduxdroid.core.di.AppModule
import com.github.s0nerik.reduxdroid.core.di.combinedReducer
import com.github.s0nerik.reduxdroid.core.di.middlewares
import com.github.s0nerik.reduxdroid.navigation.di.navForward
import com.github.s0nerik.reduxdroid.navigation.middleware.NavigationMiddleware
import com.github.s0nerik.reduxdroid_movies.auth.FacebookAuthMiddleware
import com.github.s0nerik.reduxdroid_movies.auth.FbAction
import com.github.s0nerik.reduxdroid_movies.core.middleware.IntentActionMiddleware
import me.tatarka.redux.Reducer
import org.koin.androidx.viewmodel.ext.koin.viewModel

internal class Module : AppModule({
    combinedReducer() bind Reducer::class

    middlewares {
        listOf(
                LoggingMiddleware(),
                get<IntentActionMiddleware>(),
                get<ActivityResultMiddleware>(),
                get<NavigationMiddleware>(),
                get<FacebookAuthMiddleware>()
        )
    }

    navForward<FbAction.LogOut>(R.id.action_mainFragment_to_authFragment)
    navForward<Nav.Login>(R.id.action_mainFragment_to_authFragment)
    navForward<FbAction.Login.Success>(R.id.action_authFragment_to_mainFragment)

    viewModel { AppViewModel(get(), get(), get(), get()) }
})

// Actions

internal sealed class Nav {
    object Login
}
