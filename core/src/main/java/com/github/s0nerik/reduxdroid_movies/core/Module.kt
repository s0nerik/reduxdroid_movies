package com.github.s0nerik.reduxdroid_movies.core

import com.github.s0nerik.reduxdroid.core.di.AppModule
import com.github.s0nerik.reduxdroid.state_serializer.di.stateSerializer
import com.github.s0nerik.reduxdroid_movies.core.middleware.IntentActionMiddleware
import com.github.s0nerik.reduxdroid_movies.core.middleware.IntentActionMiddlewareImpl
import com.github.s0nerik.reduxdroid_movies.core.serializers.DateTimeSerializer
import com.github.s0nerik.reduxdroid_movies.core.util.*
import kotlinx.coroutines.Dispatchers

internal class Module : AppModule({
    single { ResourceResolverImpl(get()) as ResourceResolver }

    single { IntentActionMiddlewareImpl() as IntentActionMiddleware }

    stateSerializer(DateTimeSerializer)
}) {
    init {
        initGlideConfigs()
    }
}