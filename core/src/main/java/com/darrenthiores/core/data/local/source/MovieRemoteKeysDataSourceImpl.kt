package com.darrenthiores.core.data.local.source

import com.darrenthiores.core.MovieDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import netviescore.moviedb.MovieRemoteKeys

class MovieRemoteKeysDataSourceImpl(
    db: MovieDatabase
): MovieRemoteKeysDataSource {
    private val remoteKeysQueries = db.movieRemoteKeysQueries

    override suspend fun getRemoteKeys(id: Long): MovieRemoteKeys? =
        withContext(Dispatchers.Default) {
            remoteKeysQueries
                .getRemoteKeys(id)
                .executeAsOneOrNull()
        }

    override fun addAllRemoteKeys(remoteKeys: List<MovieRemoteKeys>) {
        remoteKeysQueries.transaction {
            remoteKeys.forEach {
                remoteKeysQueries.addAllRemoteKeys(
                    id = null,
                    prevPage = it.prevPage,
                    nextPage = it.nextPage
                )
            }
        }
    }

    override fun deleteAllRemoteKeys() {
        remoteKeysQueries.deleteAllRemoteKeys()
    }

}