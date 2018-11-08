package com.github.s0nerik.reduxdroid_movies.core.base

import com.github.s0nerik.reduxdroid.core.ActionDispatcher
import com.github.s0nerik.reduxdroid.core.StateStore
import com.github.s0nerik.reduxdroid.viewmodel.ReduxViewModel
import com.github.s0nerik.reduxdroid_movies.core.util.ResourceResolver
import com.github.s0nerik.reduxdroid_movies.util.CoroutineContextHolder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel(
        store: StateStore,
        res: ResourceResolver,
        dispatcher: ActionDispatcher,
        ctx: CoroutineContextHolder
) : ReduxViewModel(store, dispatcher), ResourceResolver by res, CoroutineContextHolder by ctx, CoroutineScope {

    private val job = Job()

    override val coroutineContext: CoroutineContext
        get() = job + main

    override fun onCleared() {
        job.cancel()
    }
}