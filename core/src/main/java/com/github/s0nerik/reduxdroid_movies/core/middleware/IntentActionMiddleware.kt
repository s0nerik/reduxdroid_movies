package com.github.s0nerik.reduxdroid_movies.core.middleware

import android.app.Activity
import android.content.Intent
import com.github.s0nerik.reduxdroid.core.middleware.TypedMiddleware
import com.github.s0nerik.reduxdroid.util.weak
import com.github.s0nerik.reduxdroid_movies.core.middleware.IntentAction.UseCase.START_ACTIVITY
import com.github.s0nerik.reduxdroid_movies.core.middleware.IntentAction.UseCase.START_SERVICE
import org.koin.standalone.KoinComponent

data class IntentAction(
        val useCase: UseCase,
        val intent: Intent
) {
    enum class UseCase {
        START_ACTIVITY, START_SERVICE
    }
}

abstract class IntentActionMiddleware : TypedMiddleware<IntentAction>(IntentAction::class) {
    abstract fun attach(activity: Activity)
}

internal class IntentActionMiddlewareImpl : IntentActionMiddleware(), KoinComponent {
    private var currentActivity by weak<Activity>()

    override fun attach(activity: Activity) {
        currentActivity = activity
    }

    override fun run(next: (Any) -> Any, action: IntentAction): Any {
        when (action.useCase) {
            START_ACTIVITY -> currentActivity?.startActivity(action.intent)
            START_SERVICE -> currentActivity?.startService(action.intent)
        }
        return next(action)
    }
}