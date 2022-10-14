package com.darrenthiores.core.domain

import androidx.paging.PagingData
import com.darrenthiores.core.model.domain.MovieDomain
import kotlinx.coroutines.flow.Flow

interface MovieUseCase {
    fun getMovies(): Flow<PagingData<MovieDomain>>
}