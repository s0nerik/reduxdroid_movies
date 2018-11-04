package com.github.s0nerik.reduxdroid_movies.repo

import com.github.s0nerik.reduxdroid_movies.core_test.TestCoroutineContextHolder
import com.github.s0nerik.reduxdroid_movies.core_test.util.any
import com.github.s0nerik.reduxdroid_movies.model.Movie
import com.github.s0nerik.reduxdroid_movies.repo.local.LocalRepository
import com.github.s0nerik.reduxdroid_movies.repo.network.NetworkRepository
import kotlinx.coroutines.runBlocking
import org.joda.time.DateTime
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class MovieDbRepositoryImplTest {
    @Mock
    internal lateinit var localRepo: LocalRepository

    @Mock
    internal lateinit var networkRepo: NetworkRepository

    @Before
    fun setUp() = MockitoAnnotations.initMocks(this)

    @Test
    fun `uses only local repo if it contains data`() = runBlocking {
        // Given
        Mockito.`when`(localRepo.getMovies()).thenReturn(
                listOf(Movie(0, "", "", DateTime(0), "", false, 0f))
        )
        val hybridRepo = MovieDbRepositoryImpl(localRepo, networkRepo, TestCoroutineContextHolder)

        // When
        hybridRepo.getMovies()

        // Then
        Mockito.verify(localRepo).getMovies()
        Mockito.verifyZeroInteractions(networkRepo)
    }

    @Test
    fun `uses network repo if local repo doesn't contain data`() = runBlocking {
        // Given
        Mockito.`when`(localRepo.getMovies()).thenReturn(emptyList())
        val hybridRepo = MovieDbRepositoryImpl(localRepo, networkRepo, TestCoroutineContextHolder)

        // When
        hybridRepo.getMovies()

        // Then
        Mockito.verify(localRepo, Mockito.times(2)).getMovies()
        Mockito.verify(networkRepo).getMovies(any(), any())
        Mockito.verifyNoMoreInteractions(networkRepo)
    }
}