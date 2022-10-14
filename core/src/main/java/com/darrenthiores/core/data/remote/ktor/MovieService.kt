package com.darrenthiores.core.data.remote.ktor

import com.darrenthiores.core.model.data.MoviesResponse

interface MovieService {

    suspend fun getMovies(page: Long): MoviesResponse

}