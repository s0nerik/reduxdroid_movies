package com.github.s0nerik.reduxdroid_movies.main

import com.github.s0nerik.reduxdroid.core.ActionDispatcher
import com.github.s0nerik.reduxdroid.core.StateStore
import com.github.s0nerik.reduxdroid_movies.core.base.BaseBoundVmFragment
import com.github.s0nerik.reduxdroid_movies.core.base.BaseViewModel
import com.github.s0nerik.reduxdroid_movies.core.util.CoroutineContextHolder
import com.github.s0nerik.reduxdroid_movies.core.util.ResourceResolver
import com.github.s0nerik.reduxdroid_movies.main.databinding.FragmentMainBinding

class MainViewModel internal constructor(
    store: StateStore,
    dispatcher: ActionDispatcher,
    res: ResourceResolver,
    ctx: CoroutineContextHolder
) : BaseViewModel(store, res, dispatcher, ctx) {
}

class MainFragment : BaseBoundVmFragment<FragmentMainBinding, MainViewModel>(
    layoutId = R.layout.fragment_main,
    vmClass = MainViewModel::class,
    vmSetter = { it::setVm }
) {

}