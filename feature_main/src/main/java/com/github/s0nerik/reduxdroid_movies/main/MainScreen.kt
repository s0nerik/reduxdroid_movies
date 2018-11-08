package com.github.s0nerik.reduxdroid_movies.main

import android.os.Bundle
import android.view.View
import androidx.navigation.ui.NavigationUI
import com.github.s0nerik.reduxdroid.core.ActionDispatcher
import com.github.s0nerik.reduxdroid.core.StateStore
import com.github.s0nerik.reduxdroid_movies.auth.AuthState
import com.github.s0nerik.reduxdroid_movies.auth.FbAction
import com.github.s0nerik.reduxdroid_movies.core.base.BaseBoundVmNavigationFragment
import com.github.s0nerik.reduxdroid_movies.core.base.BaseViewModel
import com.github.s0nerik.reduxdroid_movies.util.CoroutineContextHolder
import com.github.s0nerik.reduxdroid_movies.core.util.ResourceResolver
import com.github.s0nerik.reduxdroid_movies.main.databinding.FragmentMainBinding
import kotlinx.android.synthetic.main.fragment_main.*
import org.koin.android.ext.android.inject

class MainViewModel internal constructor(
        store: StateStore,
        dispatcher: ActionDispatcher,
        res: ResourceResolver,
        ctx: CoroutineContextHolder
) : BaseViewModel(store, res, dispatcher, ctx) {
    fun logOut() {
        dispatch(FbAction.LogOut)
    }
}

class MainFragment : BaseBoundVmNavigationFragment<FragmentMainBinding, MainViewModel>(
        layoutId = R.layout.fragment_main,
        vmClass = MainViewModel::class,
        vmSetter = { it::setVm },
        navHostFragmentId = R.id.navHostFragment
) {
    val store: StateStore by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        avatarView.profileId = store.state.get(AuthState::userId)
        NavigationUI.setupWithNavController(bottomNav, navController)
    }
}