package com.darrenthiores.core.data.local.source

import netviescore.moviedb.MovieRemoteKeys

interface MovieRemoteKeysDataSource {
    suspend fun getRemoteKeys(id: Long): MovieRemoteKeys?

    fun addAllRemoteKeys(remoteKeys: List<MovieRemoteKeys>)

    fun deleteAllRemoteKeys()
}