package com.darrenthiores.core.data.remote

import com.darrenthiores.core.data.remote.ktor.MovieService
import com.darrenthiores.core.model.data.MoviesResponse

class RemoteDataSource(
    private val service: MovieService
) {
    suspend fun getMovies(page: Long): MoviesResponse = service.getMovies(page)
}