package com.github.s0nerik.reduxdroid_movies.films

import com.github.s0nerik.reduxdroid.core.ActionDispatcher
import com.github.s0nerik.reduxdroid.core.StateStore
import com.github.s0nerik.reduxdroid_movies.core.base.BaseBoundVmFragment
import com.github.s0nerik.reduxdroid_movies.core.base.BaseViewModel
import com.github.s0nerik.reduxdroid_movies.core.util.CoroutineContextHolder
import com.github.s0nerik.reduxdroid_movies.core.util.ResourceResolver
import com.github.s0nerik.reduxdroid_movies.films.databinding.FragmentFilmsBinding

class FilmsViewModel internal constructor(
    store: StateStore,
    dispatcher: ActionDispatcher,
    res: ResourceResolver,
    ctx: CoroutineContextHolder
) : BaseViewModel(store, res, dispatcher, ctx) {

}

class FilmsFragment : BaseBoundVmFragment<FragmentFilmsBinding, FilmsViewModel>(
    layoutId = R.layout.fragment_films,
    vmClass = FilmsViewModel::class,
    vmSetter = { it::setVm }
)