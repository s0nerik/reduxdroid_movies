package com.github.s0nerik.reduxdroid_movies.auth

import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.github.s0nerik.reduxdroid.core.di.AppModule
import com.github.s0nerik.reduxdroid.core.di.reducer
import com.github.s0nerik.reduxdroid.core.di.state
import com.github.s0nerik.reduxdroid.navigation.di.navBack
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import org.koin.androidx.viewmodel.ext.koin.viewModel

internal class Module : AppModule({
    val accessToken = AccessToken.getCurrentAccessToken()
    val userId = if (accessToken != null && !accessToken.isExpired) accessToken.userId else ""
    state(AuthState(userId = userId))

    single { CallbackManager.Factory.create() as CallbackManager }
    single { LoginManager.getInstance() as LoginManager }
    single { FacebookAuthMiddlewareImpl(get(), get()) } bind FacebookAuthMiddleware::class

    reducer(::fbLoginStart)
    reducer(::fbLoginSuccess)
    reducer(::fbLoginError)

    navBack<FbLogin.Success>(dropOriginalAction = false)

    viewModel { AuthViewModel(get(), get(), get(), get()) }
})

// State

@Serializable
data class AuthState internal constructor(
    val userId: String = "",
    val isInProgress: Boolean = false
) {
    @Transient
    val isLoggedIn: Boolean
        get() = userId.isNotBlank()
}

// Actions

sealed class FbLogin {
    object Start : FbLogin()
    data class Success internal constructor(val loginResult: LoginResult) : FbLogin()
    data class Error internal constructor(val error: Throwable) : FbLogin()
}

// Reducers

internal fun fbLoginStart(a: FbLogin.Start, s: AuthState) = s.copy(
    isInProgress = true
)

internal fun fbLoginSuccess(a: FbLogin.Success, s: AuthState) = s.copy(
    isInProgress = false,
    userId = a.loginResult.accessToken.userId
)

internal fun fbLoginError(a: FbLogin.Error, s: AuthState) = s.copy(
    isInProgress = false,
    userId = ""
)