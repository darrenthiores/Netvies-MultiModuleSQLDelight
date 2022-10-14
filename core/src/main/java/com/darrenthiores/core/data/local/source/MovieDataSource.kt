package com.darrenthiores.core.data.local.source

import androidx.paging.PagingSource
import netviescore.moviedb.MovieEntity

interface MovieDataSource {
    fun insert(movie: List<MovieEntity>)

    fun getMovie(): PagingSource<Long, MovieEntity>

    fun clearAll()
}