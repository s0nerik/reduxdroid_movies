package com.github.s0nerik.reduxdroid_movies

import com.github.s0nerik.reduxdroid.core.ActionDispatcher
import com.github.s0nerik.reduxdroid.core.StateStore
import com.github.s0nerik.reduxdroid_movies.auth.AuthState
import com.github.s0nerik.reduxdroid_movies.core.base.BaseViewModel
import com.github.s0nerik.reduxdroid_movies.util.CoroutineContextHolder
import com.github.s0nerik.reduxdroid_movies.core.util.ResourceResolver

class AppViewModel internal constructor(
    store: StateStore,
    dispatcher: ActionDispatcher,
    res: ResourceResolver,
    ctx: CoroutineContextHolder
) : BaseViewModel(store, res, dispatcher, ctx) {
    fun ensureLoggedIn() {
        if (!currentState.get(AuthState::isLoggedIn))
            dispatch(Nav.Login)
    }
}