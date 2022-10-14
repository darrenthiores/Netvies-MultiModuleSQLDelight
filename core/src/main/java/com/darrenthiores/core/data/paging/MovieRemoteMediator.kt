package com.darrenthiores.core.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.darrenthiores.core.data.local.LocalDataSource
import com.darrenthiores.core.data.remote.RemoteDataSource
import com.darrenthiores.core.utils.DataMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import netviescore.moviedb.MovieEntity
import netviescore.moviedb.MovieRemoteKeys
import timber.log.Timber

@ExperimentalPagingApi
class MovieRemoteMediator(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
): RemoteMediator<Long, MovieEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Long, MovieEntity>
    ): MediatorResult {
        return try {
            val currentPage = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKeys?.nextPage?.minus(1) ?: 1
                }
                LoadType.PREPEND -> {
                    val remoteKeys = getRemoteKeyForFirstItem(state)
                    val prevPage = remoteKeys?.prevPage
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    prevPage
                }
                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    val nextPage = remoteKeys?.nextPage
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    nextPage
                }
            }

            Timber.d("$currentPage")

            val response = remoteDataSource.getMovies(page = currentPage)
            val endOfPaginationReached = response.results.isEmpty()

            val prevPage = if (currentPage == 1L) null else currentPage - 1
            val nextPage = if (endOfPaginationReached) null else currentPage + 1

            withContext(Dispatchers.IO) {
                localDataSource.movieDb().transaction {
                    if (loadType == LoadType.REFRESH) {
                        localDataSource.clearAll()
                        localDataSource.deleteAllRemoteKeys()
                    }
                    val keys = response.results.map {
                        MovieRemoteKeys(
                            id = 0,
                            prevPage = prevPage,
                            nextPage = nextPage
                        )
                    }
                    localDataSource.addAllRemoteKeys(remoteKeys = keys)
                    localDataSource.insert(response.results.map { DataMapper.mapResponsesToEntities(it) })
                }
            }
            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Long, MovieEntity>
    ): MovieRemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.remoteId?.let { id ->
                localDataSource.getRemoteKeys(id = id)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Long, MovieEntity>
    ): MovieRemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { movie ->
                localDataSource.getRemoteKeys(id = movie.remoteId)
            }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Long, MovieEntity>
    ): MovieRemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { movie ->
                localDataSource.getRemoteKeys(id = movie.remoteId)
            }
    }

}