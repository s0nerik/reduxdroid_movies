package com.github.s0nerik.reduxdroid_movies.repo

import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.github.s0nerik.reduxdroid.testing.runBlockingTest
import com.github.s0nerik.reduxdroid_movies.core_test.TestCoroutineContextHolder
import com.github.s0nerik.reduxdroid_movies.model.Movie
import com.github.s0nerik.reduxdroid_movies.repo.local.LocalRepository
import com.github.s0nerik.reduxdroid_movies.repo.local.model.MyObjectBox
import io.objectbox.BoxStore
import org.joda.time.DateTime
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import strikt.api.expectThat
import strikt.assertions.first
import strikt.assertions.get
import strikt.assertions.hasSize
import strikt.assertions.isTrue

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
    fun `saves all movies it gets`() = runBlockingTest {
        // Given
        val movies = (1..10).map { movie.copy(id = it.toLong()) }

        // When
        localRepo.replaceMovies(movies)
        val dbMovies = localRepo.getMovies()

        // Then
        expectThat(dbMovies).hasSize(10)
    }

    @Test
    fun `updates isFavorite of existing movies`() = runBlockingTest {
        // Given
        val oldMovies = listOf(movie.copy(id = 1, isFavorite = false))
        val newMovies = listOf(movie.copy(id = 1, isFavorite = true))

        // When
        localRepo.replaceMovies(oldMovies)
        localRepo.replaceMovies(newMovies)
        val dbMovies = localRepo.getMovies()

        // Then
        expectThat(dbMovies)[0].get { isFavorite }.isTrue()
    }

    @Test
    fun `keeps favorite status upon replacing movies`() = runBlockingTest {
        // Given
        val oldMovies = listOf(movie.copy(id = 1, isFavorite = true))
        val newMovies = listOf(movie.copy(id = 1, isFavorite = false))

        // When
        localRepo.replaceMovies(oldMovies)
        localRepo.replaceMovies(newMovies)
        val dbMovies = localRepo.getMovies()

        // Then
        expectThat(dbMovies)[0].get { isFavorite }.isTrue()
    }

    @Test
    fun `keeps favorite movies if they're not present in a new list`() = runBlockingTest {
        // Given
        val oldMovies = listOf(movie.copy(id = 1, isFavorite = true))
        val newMovies = listOf(movie.copy(id = 2, isFavorite = false))

        // When
        localRepo.replaceMovies(oldMovies)
        localRepo.replaceMovies(newMovies)
        val dbMovies = localRepo.getMovies()

        // Then
        expectThat(dbMovies) {
            hasSize(2)
            first { it.id == 1L }.get { isFavorite }.isTrue()
        }
    }
}