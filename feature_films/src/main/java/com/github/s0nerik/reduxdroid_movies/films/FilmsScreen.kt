package com.github.s0nerik.reduxdroid_movies.films

import android.os.Bundle
import android.view.View
import com.github.s0nerik.reduxdroid.core.ActionDispatcher
import com.github.s0nerik.reduxdroid.core.StateStore
import com.github.s0nerik.reduxdroid.livedata.get
import com.github.s0nerik.reduxdroid_movies.core.base.BaseBoundVmFragment
import com.github.s0nerik.reduxdroid_movies.core.base.BaseViewModel
import com.github.s0nerik.reduxdroid_movies.core.ui.FilmItem
import com.github.s0nerik.reduxdroid_movies.core.util.*
import com.github.s0nerik.reduxdroid_movies.films.databinding.FragmentFilmsBinding
import com.github.s0nerik.reduxdroid_movies.model.Movie
import com.github.s0nerik.reduxdroid_movies.repo.MovieDbRepository
import com.github.s0nerik.reduxdroid_movies.shared_state.*
import kotlinx.android.synthetic.main.fragment_films.*
import me.tatarka.bindingcollectionadapter2.itembindings.OnItemBindClass
import me.tatarka.bindingcollectionadapter2.map

class FilmsViewModel internal constructor(
        store: StateStore,
        dispatcher: ActionDispatcher,
        res: ResourceResolver,
        ctx: CoroutineContextHolder,
        private val repo: MovieDbRepository
) : BaseViewModel(store, res, dispatcher, ctx), FilmItem.Listener {
    val items = state.get(SharedState::films).map(this::groupedByMonth)
    val diff = genericDiffCallback<FilmItem> { old, new -> old.movie.id == new.movie.id }
    val itemBinding = OnItemBindClass<Any>().apply {
        map<FilmItem> { itemBinding, _, _ ->
            itemBinding.set(BR.item, R.layout.item_film)
                    .bindExtra(BR.listener, this@FilmsViewModel)
        }
        map<FilmsHeaderItem>(BR.item, R.layout.item_films_header)
    }

    val isLoading = state.get(SharedState::isLoading)
    val isRefreshing = state.get(SharedState::isRefreshing)
    val loadingError = state.get(SharedState::loadingError, "")

    fun loadItems() {
        if (currentState.get(SharedState::films).isEmpty())
            dispatch(loadFilms(repo))
    }

    fun groupedByMonth(items: List<Movie>): List<Any> {
        val mappings = items.sortedByDescending { it.releaseDate }
                .groupBy { it.releaseDate.monthOfYear() }
                .mapValues { it.value.sortedByDescending { it.rating } }

        val groupedItems = mutableListOf<Any>()
        mappings.keys.forEach { month ->
            groupedItems += FilmsHeaderItem("${month.asText}, ${month.dateTime.year}")
            mappings[month]!!.forEach {
                groupedItems += FilmItem(it)
            }
        }

        return groupedItems
    }

    fun refresh() {
        if (currentState.get(SharedState::canRefresh))
            dispatch(refreshFilms(repo))
    }

    override fun toggleFavorite(item: FilmItem) {
        dispatch(toggleFavorite(repo, item.movie))
    }

    override fun share(item: FilmItem) {
        dispatch(share(item.movie))
    }
}

class FilmsFragment : BaseBoundVmFragment<FragmentFilmsBinding, FilmsViewModel>(
        layoutId = R.layout.fragment_films,
        vmClass = FilmsViewModel::class,
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