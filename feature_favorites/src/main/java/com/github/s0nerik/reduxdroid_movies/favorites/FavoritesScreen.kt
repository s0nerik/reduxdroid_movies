package com.github.s0nerik.reduxdroid_movies.favorites

import android.os.Bundle
import android.view.View
import com.github.s0nerik.reduxdroid.core.ActionDispatcher
import com.github.s0nerik.reduxdroid.core.StateStore
import com.github.s0nerik.reduxdroid.livedata.get
import com.github.s0nerik.reduxdroid_movies.core.base.BaseBoundVmFragment
import com.github.s0nerik.reduxdroid_movies.core.base.BaseViewModel
import com.github.s0nerik.reduxdroid_movies.core.ui.FilmItem
import com.github.s0nerik.reduxdroid_movies.core.util.*
import com.github.s0nerik.reduxdroid_movies.favorites.databinding.FragmentFavoritesBinding
import com.github.s0nerik.reduxdroid_movies.repo.MovieDbRepository
import com.github.s0nerik.reduxdroid_movies.shared_state.SharedState
import com.github.s0nerik.reduxdroid_movies.shared_state.loadFilms
import com.github.s0nerik.reduxdroid_movies.shared_state.share
import com.github.s0nerik.reduxdroid_movies.shared_state.toggleFavorite
import kotlinx.android.synthetic.main.fragment_favorites.*
import me.tatarka.bindingcollectionadapter2.itemBindingOf

class FavoritesViewModel internal constructor(
        store: StateStore,
        dispatcher: ActionDispatcher,
        res: ResourceResolver,
        ctx: CoroutineContextHolder,
        private val repo: MovieDbRepository
) : BaseViewModel(store, res, dispatcher, ctx), FilmItem.Listener {
    val items = state.get(SharedState::favorites).mapItems { FilmItem(it) }
    val diff = diffCallback<FilmItem> { old, new -> old.movie.id == new.movie.id }
    val itemBinding = itemBindingOf<FilmItem>(BR.item, R.layout.item_film)
            .bindExtra(BR.listener, this)

    val isEmpty = state.get(SharedState::isFavoritesEmpty)
    val isLoading = state.get(SharedState::isLoading)
    val loadingError = state.get(SharedState::loadingError, "")

    fun loadItems() {
        if (currentState.get(SharedState::films).isEmpty())
            dispatch(loadFilms(repo))
    }

    override fun toggleFavorite(item: FilmItem) {
        dispatch(toggleFavorite(repo, item.movie))
    }

    override fun share(item: FilmItem) {
        dispatch(share(item.movie))
    }
}

class FavoritesFragment : BaseBoundVmFragment<FragmentFavoritesBinding, FavoritesViewModel>(
        layoutId = R.layout.fragment_favorites,
        vmClass = FavoritesViewModel::class,
        vmSetter = { it::setVm }
) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (savedInstanceState == null) {
            vm.loadItems()
        } else {
            recycler.restoreState(savedInstanceState)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        recycler.saveState(outState)
    }
}