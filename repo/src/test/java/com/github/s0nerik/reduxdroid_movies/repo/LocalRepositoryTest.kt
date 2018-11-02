package com.github.s0nerik.reduxdroid_movies.repo

import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.github.s0nerik.reduxdroid_movies.core_test.TestCoroutineContextHolder
import com.github.s0nerik.reduxdroid_movies.model.Movie
import com.github.s0nerik.reduxdroid_movies.repo.local.LocalRepository
import com.github.s0nerik.reduxdroid_movies.repo.local.model.MyObjectBox
import io.objectbox.BoxStore
import kotlinx.coroutines.runBlocking
import org.joda.time.DateTime
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LocalRepositoryTest {
    private lateinit var boxStore: BoxStore

    private val localRepo
        get() = LocalRepository(boxStore, TestCoroutineContextHolder)

    private val movie = Movie(0, "", "", DateTime.now(), "", false, 0f)

    @Before
    fun before() {
        boxStore = MyObjectBox.builder().androidContext(ApplicationProvider.getApplicationContext()).build()
    }

    @After
    fun after() {
        boxStore.close()
        boxStore.deleteAllFiles()
    }

    @Test
    fun `saves all movies it gets`() = runBlocking {
        // Given
        val movies = (1..10).map { movie.copy(id = it.toLong()) }

        // When
        localRepo.replaceMovies(movies)
        val dbMovies = localRepo.getMovies()

        // Then
        Assert.assertEquals(10, dbMovies.size)
    }

    @Test
    fun `updates isFavorite of existing movies`() = runBlocking {
        // Given
        val oldMovies = listOf(movie.copy(id = 1, isFavorite = false))
        val newMovies = listOf(movie.copy(id = 1, isFavorite = true))

        // When
        localRepo.replaceMovies(oldMovies)
        localRepo.replaceMovies(newMovies)
        val dbMovies = localRepo.getMovies()

        // Then
        Assert.assertEquals(true, dbMovies[0].isFavorite)
    }

    @Test
    fun `keeps favorite status upon replacing movies`() = runBlocking {
        // Given
        val oldMovies = listOf(movie.copy(id = 1, isFavorite = true))
        val newMovies = listOf(movie.copy(id = 1, isFavorite = false))

        // When
        localRepo.replaceMovies(oldMovies)
        localRepo.replaceMovies(newMovies)
        val dbMovies = localRepo.getMovies()

        // Then
        Assert.assertEquals(true, dbMovies[0].isFavorite)
    }

    @Test
    fun `keeps favorite movies if they're not present in a new list`() = runBlocking {
        // Given
        val oldMovies = listOf(movie.copy(id = 1, isFavorite = true))
        val newMovies = listOf(movie.copy(id = 2, isFavorite = false))

        // When
        localRepo.replaceMovies(oldMovies)
        localRepo.replaceMovies(newMovies)
        val dbMovies = localRepo.getMovies()

        // Then
        Assert.assertEquals(2, dbMovies.size)
        Assert.assertEquals(true, dbMovies.first { it.id == 1L }.isFavorite)
    }
}