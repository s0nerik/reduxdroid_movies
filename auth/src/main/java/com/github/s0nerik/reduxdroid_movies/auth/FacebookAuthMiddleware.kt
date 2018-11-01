package com.github.s0nerik.reduxdroid_movies.auth

import androidx.fragment.app.Fragment
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.github.s0nerik.reduxdroid.core.ActionDispatcher
import com.github.s0nerik.reduxdroid.core.middleware.TypedMiddleware
import com.github.s0nerik.reduxdroid.util.weak
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject
import java.util.concurrent.CancellationException

abstract class FacebookAuthMiddleware : TypedMiddleware<FbAction>(FbAction::class)

internal class FacebookAuthMiddlewareImpl(
    private val loginManager: LoginManager,
    private val callbackManager: CallbackManager
) : FacebookAuthMiddleware(), KoinComponent {
    var fragment by weak<Fragment?>(null)

    private val dispatcher: ActionDispatcher by inject()

    init {
        loginManager.registerCallback(callbackManager, object: FacebookCallback<LoginResult> {
            override fun onSuccess(result: LoginResult) {
                dispatcher.dispatch(FbAction.Login.Success(result))
            }

            override fun onCancel() {
                dispatcher.dispatch(FbAction.Login.Error(CancellationException()))
            }

            override fun onError(error: FacebookException) {
                dispatcher.dispatch(FbAction.Login.Error(error))
            }
        })
    }

    override fun run(next: (Any) -> Any, action: FbAction): Any {
        when (action) {
            is FbAction.Login.Start -> {
                fragment?.let { loginManager.logInWithReadPermissions(fragment, listOf("email")) }
            }
            is FbAction.LogOut -> {
                loginManager.logOut()
            }
        }
        return next(action)
    }
}