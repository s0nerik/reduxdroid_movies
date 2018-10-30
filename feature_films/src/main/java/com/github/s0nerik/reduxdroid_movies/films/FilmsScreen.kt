package com.github.s0nerik.reduxdroid_movies.films

import android.os.Bundle
import com.github.s0nerik.reduxdroid.core.ActionDispatcher
import com.github.s0nerik.reduxdroid.core.StateStore
import com.github.s0nerik.reduxdroid.livedata.get
import com.github.s0nerik.reduxdroid_movies.core.base.BaseBoundVmFragment
import com.github.s0nerik.reduxdroid_movies.core.base.BaseViewModel
import com.github.s0nerik.reduxdroid_movies.core.util.*
import com.github.s0nerik.reduxdroid_movies.films.databinding.FragmentFilmsBinding
import com.github.s0nerik.reduxdroid_movies.model.Movie
import com.github.s0nerik.reduxdroid_movies.repo.MovieDbRepository
import me.tatarka.bindingcollectionadapter2.itembindings.OnItemBindClass
import me.tatarka.bindingcollectionadapter2.map

class FilmsViewModel internal constructor(
    store: StateStore,
    dispatcher: ActionDispatcher,
    res: ResourceResolver,
    ctx: CoroutineContextHolder,
    private val repo: MovieDbRepository
) : BaseViewModel(store, res, dispatcher, ctx), FilmsItem.Listener {
    val items = state.get(FilmsState::items).map(this::groupedByMonth)
    val isLoading = state.get(FilmsState::isLoading)

    val diff = genericDiffCallback<FilmsItem>(
        areItemsTheSame = { old, new -> old.movie.id == new.movie.id }
    )

    val itemBinding = OnItemBindClass<Any>().apply {
        map<FilmsItem> { itemBinding, _, _ ->
            itemBinding.set(BR.item, R.layout.item_films)
                .bindExtra(BR.listener, this@FilmsViewModel)
        }
        map<FilmsHeaderItem>(BR.item, R.layout.item_films_header)
    }

    fun loadItems() {
        dispatch(loadFilms(repo))
    }

    fun groupedByMonth(items: List<Movie>): List<Any> {
        val mappings = items.sortedByDescending { it.releaseDate }
            .groupBy { it.releaseDate.monthOfYear().asShortText }
            .mapValues { it.value.sortedByDescending { it.rating } }

        val groupedItems = mutableListOf<Any>()
        mappings.keys.forEach { date ->
            groupedItems += FilmsHeaderItem(date)
            mappings[date]!!.forEach {
                groupedItems += FilmsItem(it)
            }
        }

        return groupedItems
    }

    override fun toggleFavorite(movie: Movie) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun share(movie: Movie) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}

class FilmsFragment : BaseBoundVmFragment<FragmentFilmsBinding, FilmsViewModel>(
    layoutId = R.layout.fragment_films,
    vmClass = FilmsViewModel::class,
    vmSetter = { it::setVm }
) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null)
            vm.loadItems()
    }
}