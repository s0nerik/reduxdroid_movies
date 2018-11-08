package com.github.s0nerik.reduxdroid_movies.repo

import com.github.s0nerik.reduxdroid.testing.runBlockingTest
import com.github.s0nerik.reduxdroid_movies.core_test.TestCoroutineContextHolder
import com.github.s0nerik.reduxdroid_movies.repo.network.MovieDbService
import com.github.s0nerik.reduxdroid_movies.repo.network.NetworkRepository
import com.github.s0nerik.reduxdroid_movies.repo.network.model.ApiMovie
import com.github.s0nerik.reduxdroid_movies.repo.network.model.ApiMoviesPage
import kotlinx.coroutines.async
import org.joda.time.DateTime
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import strikt.api.expectThat
import strikt.assertions.hasSize

class NetworkRepositoryTest {
    @Mock
    internal lateinit var apiService: MovieDbService

    @Before
    fun setUp() = MockitoAnnotations.initMocks(this)

    @Test
    fun `loads all available pages at once`() = runBlockingTest {
        // Given
        Mockito.`when`(
                apiService.getMoviesForPeriod(
                        Mockito.anyString(),
                        Mockito.anyString(),
                        Mockito.anyInt(),
                        Mockito.anyString(),
                        Mockito.anyBoolean(),
                        Mockito.anyString()
                )
        ).thenAnswer {
            async {
                val page = it.getArgument<Int>(2)
                when (page) {
                    in 1..10 -> ApiMoviesPage(page, 2 * 10, 10, listOf(
                            Mockito.mock(ApiMovie::class.java),
                            Mockito.mock(ApiMovie::class.java)
                    ))
                    else -> error("Requested more than should")
                }
            }
        }
        val networkRepo = NetworkRepository(apiService, TestCoroutineContextHolder)

        // When
        val movies = networkRepo.getMovies(DateTime.now(), DateTime.now())

        // Then
        expectThat(movies).hasSize(2 * 10)
    }
}