package com.darrenthiores.core.data.repository

import androidx.paging.PagingData
import com.darrenthiores.core.model.domain.MovieDomain
import kotlinx.coroutines.flow.Flow

interface IMovieRepository {
    fun getMovies(): Flow<PagingData<MovieDomain>>
}