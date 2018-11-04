package com.github.s0nerik.reduxdroid_movies.shared_state

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.github.s0nerik.reduxdroid.core.ActionDispatcher
import com.github.s0nerik.reduxdroid_movies.core_test.TestMiddleware
import com.github.s0nerik.reduxdroid_movies.core_test.appModules
import com.github.s0nerik.reduxdroid_movies.core_test.testModule
import com.github.s0nerik.reduxdroid_movies.core_test.util.runBlockingTest
import com.github.s0nerik.reduxdroid_movies.model.Movie
import com.github.s0nerik.reduxdroid_movies.repo.MovieDbRepository
import org.hamcrest.CoreMatchers.instanceOf
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.standalone.StandAloneContext.startKoin
import org.koin.standalone.inject
import org.koin.test.AutoCloseKoinTest
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@RunWith(AndroidJUnit4::class)
class ActionCreatorTest : AutoCloseKoinTest() {
    @Mock
    internal lateinit var repo: MovieDbRepository

    private val dispatcher: ActionDispatcher by inject()
    private val testMiddleware: TestMiddleware by inject()

    @Before
    fun before() {
        MockitoAnnotations.initMocks(this)

        startKoin(appModules + testModule())
    }

    @Test
    fun `loadFilms dispatches appropriate actions upon successful loading`() = runBlockingTest {
        // Given
        Mockito.`when`(repo.getMovies()).thenReturn(emptyList())

        // When
        dispatcher.dispatch(loadFilms(repo))

        // Then
        Assert.assertEquals(2, testMiddleware.actions.size)
        Assert.assertThat(testMiddleware.actions[0], instanceOf(Loading.Start::class.java))
        Assert.assertThat(testMiddleware.actions[1], instanceOf(Loading.Success::class.java))
        Assert.assertEquals(emptyList<Movie>(), testMiddleware.states.last().get<SharedState>().films)
    }

    @Test
    fun `loadFilms dispatches appropriate actions upon failed loading`() = runBlockingTest {
        // Given
        Mockito.`when`(repo.getMovies()).thenThrow(IllegalStateException("test error"))

        // When
        dispatcher.dispatch(loadFilms(repo))

        // Then
        Assert.assertEquals(2, testMiddleware.actions.size)
        Assert.assertThat(testMiddleware.actions[0], instanceOf(Loading.Start::class.java))
        Assert.assertThat(testMiddleware.actions[1], instanceOf(Loading.Error::class.java))
        Assert.assertEquals("test error", testMiddleware.states.last().get<SharedState>().loadingError)
    }
}