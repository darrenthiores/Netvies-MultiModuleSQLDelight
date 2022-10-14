package com.darrenthiores.core.domain

import androidx.paging.PagingData
import com.darrenthiores.core.data.repository.IMovieRepository
import com.darrenthiores.core.model.domain.MovieDomain
import kotlinx.coroutines.flow.Flow

class MovieInteractor(
    private val moviesRepository: IMovieRepository
): MovieUseCase {
    override fun getMovies(): Flow<PagingData<MovieDomain>> =
        moviesRepository.getMovies()
}