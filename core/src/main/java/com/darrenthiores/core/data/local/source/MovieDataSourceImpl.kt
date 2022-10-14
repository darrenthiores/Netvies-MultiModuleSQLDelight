package com.darrenthiores.core.data.local.source

import androidx.paging.PagingSource
import com.darrenthiores.core.MovieDatabase
import com.squareup.sqldelight.android.paging3.QueryPagingSource
import kotlinx.coroutines.Dispatchers
import netviescore.moviedb.MovieEntity

class MovieDataSourceImpl(
    db: MovieDatabase
): MovieDataSource {
    private val movieQueries = db.movieEntityQueries

    override fun insert(movie: List<MovieEntity>) {
        movieQueries.transaction {
            movie.forEach {
                movieQueries.insert(
                    remoteId = null,
                    id = it.id,
                    title = it.title,
                    poster_path = it.poster_path,
                    release_date = it.release_date,
                    vote_average = it.vote_average
                )
            }
        }
    }

    override fun getMovie(): PagingSource<Long, MovieEntity> =
        QueryPagingSource(
            countQuery = movieQueries.count(),
            transacter = movieQueries,
            dispatcher = Dispatchers.IO,
            queryProvider = movieQueries::getMovie
        )

    override fun clearAll() {
        movieQueries.clearAll()
    }
}