package com.github.s0nerik.reduxdroid_movies.films

import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.util.SparseArray
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
    val items = state.get(FilmsState::items).map(this::groupedByMonth)
    val isLoading = state.get(FilmsState::isLoading)

    val diff = genericDiffCallback<FilmItem>(
        areItemsTheSame = { old, new -> old.movie.id == new.movie.id }
    )

    val itemBinding = OnItemBindClass<Any>().apply {
        map<FilmItem> { itemBinding, _, _ ->
            itemBinding.set(BR.item, R.layout.item_film)
                .bindExtra(BR.listener, this@FilmsViewModel)
        }
        map<FilmsHeaderItem>(BR.item, R.layout.item_films_header)
    }

    fun loadItems() {
        if (currentState.get(FilmsState::items).isEmpty())
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        savedInstanceState?.getParcelable<Parcelable>(RECYCLER_STATE)?.let {
            recycler.layoutManager?.onRestoreInstanceState(it)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        recycler.layoutManager?.onSaveInstanceState()?.let {
            outState.putParcelable(RECYCLER_STATE, it)
        }
    }

    companion object {
        private const val RECYCLER_STATE = "RECYCLER_STATE"
    }
}