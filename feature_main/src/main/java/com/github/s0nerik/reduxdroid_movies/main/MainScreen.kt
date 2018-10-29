package com.github.s0nerik.reduxdroid_movies.main

import android.os.Bundle
import android.view.View
import com.github.s0nerik.reduxdroid.core.ActionDispatcher
import com.github.s0nerik.reduxdroid.core.StateStore
import com.github.s0nerik.reduxdroid_movies.auth.AuthState
import com.github.s0nerik.reduxdroid_movies.core.base.BaseBoundVmFragment
import com.github.s0nerik.reduxdroid_movies.core.base.BaseViewModel
import com.github.s0nerik.reduxdroid_movies.core.util.CoroutineContextHolder
import com.github.s0nerik.reduxdroid_movies.core.util.ResourceResolver
import com.github.s0nerik.reduxdroid_movies.main.databinding.FragmentMainBinding
import kotlinx.android.synthetic.main.fragment_main.*
import org.koin.android.ext.android.inject

class MainViewModel internal constructor(
    store: StateStore,
    dispatcher: ActionDispatcher,
    res: ResourceResolver,
    ctx: CoroutineContextHolder
) : BaseViewModel(store, res, dispatcher, ctx)

class MainFragment : BaseBoundVmFragment<FragmentMainBinding, MainViewModel>(
    layoutId = R.layout.fragment_main,
    vmClass = MainViewModel::class,
    vmSetter = { it::setVm }
) {
    val store: StateStore by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        avatarView.profileId = store.state.get(AuthState::userId)
    }
}