package com.darrenthiores.core.data.local

import androidx.paging.PagingSource
import com.darrenthiores.core.MovieDatabase
import com.darrenthiores.core.data.local.source.MovieDataSource
import com.darrenthiores.core.data.local.source.MovieRemoteKeysDataSource
import netviescore.moviedb.MovieEntity
import netviescore.moviedb.MovieRemoteKeys

class LocalDataSource(
    private val movieDatabase: MovieDatabase,
    private val movieDataSource: MovieDataSource,
    private val movieRemoteKeysDataSource: MovieRemoteKeysDataSource
) {
    fun movieDb(): MovieDatabase = movieDatabase

    fun insert(movie: List<MovieEntity>) {
        movieDataSource.insert(movie)
    }

    fun getMovie(): PagingSource<Long, MovieEntity> = movieDataSource.getMovie()

    fun clearAll() = movieDataSource.clearAll()

    suspend fun getRemoteKeys(id: Long): MovieRemoteKeys? = movieRemoteKeysDataSource.getRemoteKeys(id)

    fun addAllRemoteKeys(remoteKeys: List<MovieRemoteKeys>) = movieRemoteKeysDataSource.addAllRemoteKeys(remoteKeys)

    fun deleteAllRemoteKeys() = movieRemoteKeysDataSource.deleteAllRemoteKeys()

}