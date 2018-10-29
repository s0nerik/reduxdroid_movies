package com.github.s0nerik.reduxdroid_movies.favorites

import com.github.s0nerik.reduxdroid.core.ActionDispatcher
import com.github.s0nerik.reduxdroid.core.StateStore
import com.github.s0nerik.reduxdroid_movies.core.base.BaseBoundVmFragment
import com.github.s0nerik.reduxdroid_movies.core.base.BaseViewModel
import com.github.s0nerik.reduxdroid_movies.core.util.CoroutineContextHolder
import com.github.s0nerik.reduxdroid_movies.core.util.ResourceResolver
import com.github.s0nerik.reduxdroid_movies.favorites.databinding.FragmentFavoritesBinding

class FavoritesViewModel internal constructor(
    store: StateStore,
    dispatcher: ActionDispatcher,
    res: ResourceResolver,
    ctx: CoroutineContextHolder
) : BaseViewModel(store, res, dispatcher, ctx) {

}

class FavoritesFragment : BaseBoundVmFragment<FragmentFavoritesBinding, FavoritesViewModel>(
    layoutId = R.layout.fragment_favorites,
    vmClass = FavoritesViewModel::class,
    vmSetter = { it::setVm }
)