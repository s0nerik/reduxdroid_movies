package com.github.s0nerik.reduxdroid_movies.shared_state

import com.github.s0nerik.reduxdroid.core.ActionDispatcher
import com.github.s0nerik.reduxdroid.testing.*
import com.github.s0nerik.reduxdroid_movies.repo.MovieDbRepository
import org.junit.Before
import org.junit.Test
import org.koin.standalone.StandAloneContext.startKoin
import org.koin.standalone.inject
import org.koin.test.AutoCloseKoinTest
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import strikt.api.expectThat
import strikt.assertions.hasSize
import strikt.assertions.isA
import strikt.assertions.isEmpty
import strikt.assertions.isEqualTo

class ActionCreatorTest : AutoCloseKoinTest() {
    @Mock
    internal lateinit var repo: MovieDbRepository

    private val dispatcher: ActionDispatcher by inject()
    private val testMiddleware: TestMiddleware by inject()

    @Before
    fun setUp() = MockitoAnnotations.initMocks(this)

    @Before
    fun before() {
        startKoin(appModules("com.github.s0nerik.reduxdroid_movies") + testModules())
    }

    @Test
    fun `loadFilms dispatches appropriate actions upon successful loading`() = runBlockingTest {
        // Given
        Mockito.`when`(repo.getMovies()).thenReturn(emptyList())

        // When
        dispatcher.dispatch(loadFilms(repo))

        // Then
        with(testMiddleware) {
            expectThat(actions).hasSize(2)
            expectThat(actions[0]).isA<Loading.Start>()
            expectThat(actions[1]).isA<Loading.Success>()
            expectThat(lastState.get(SharedState::films)).isEmpty()
        }
    }

    @Test
    fun `loadFilms dispatches appropriate actions upon failed loading`() = runBlockingTest {
        // Given
        Mockito.`when`(repo.getMovies()).thenThrow(IllegalStateException("test error"))

        // When
        dispatcher.dispatch(loadFilms(repo))

        // Then
        with(testMiddleware) {
            expectThat(actions).hasSize(2)
            expectThat(actions[0]).isA<Loading.Start>()
            expectThat(actions[1]).isA<Loading.Error>()
            expectThat(lastState.get(SharedState::loadingError)).isEqualTo("test error")
        }
    }
}