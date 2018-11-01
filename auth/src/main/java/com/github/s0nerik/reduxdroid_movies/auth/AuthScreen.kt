package com.github.s0nerik.reduxdroid_movies.auth

import android.content.Intent
import android.os.Bundle
import com.facebook.CallbackManager
import com.github.s0nerik.reduxdroid.core.ActionDispatcher
import com.github.s0nerik.reduxdroid.core.StateStore
import com.github.s0nerik.reduxdroid_movies.auth.databinding.FragmentAuthBinding
import com.github.s0nerik.reduxdroid_movies.core.base.BaseBoundVmFragment
import com.github.s0nerik.reduxdroid_movies.core.base.BaseViewModel
import com.github.s0nerik.reduxdroid_movies.core.util.CoroutineContextHolder
import com.github.s0nerik.reduxdroid_movies.core.util.ResourceResolver
import org.koin.android.ext.android.inject

class AuthViewModel internal constructor(
        store: StateStore,
        dispatcher: ActionDispatcher,
        res: ResourceResolver,
        ctx: CoroutineContextHolder
) : BaseViewModel(store, res, dispatcher, ctx) {
    fun login() {
        dispatch(FbAction.Login.Start)
    }
}

class AuthFragment : BaseBoundVmFragment<FragmentAuthBinding, AuthViewModel>(
    layoutId = R.layout.fragment_auth,
    vmClass = AuthViewModel::class,
    vmSetter = { it::setVm }
) {
    private val callbackManager: CallbackManager by inject()
    private val authMiddleware: FacebookAuthMiddlewareImpl by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        authMiddleware.fragment = this
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }
}