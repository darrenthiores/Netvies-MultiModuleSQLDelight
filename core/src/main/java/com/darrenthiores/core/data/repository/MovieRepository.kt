package com.darrenthiores.core.data.repository

import androidx.paging.*
import com.darrenthiores.core.data.local.LocalDataSource
import com.darrenthiores.core.data.paging.MovieRemoteMediator
import com.darrenthiores.core.data.remote.RemoteDataSource
import com.darrenthiores.core.model.domain.MovieDomain
import com.darrenthiores.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

const val LOAD_SIZE = 20

@ExperimentalPagingApi
class MovieRepository(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
): IMovieRepository {
    override fun getMovies(): Flow<PagingData<MovieDomain>> {
        val pagingSourceFactory = { localDataSource.getMovie() }
        return Pager(
            config = PagingConfig(pageSize = LOAD_SIZE, enablePlaceholders = true),
            remoteMediator = MovieRemoteMediator(
                localDataSource, remoteDataSource
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow.map { pagingData ->
            pagingData.map {
                DataMapper.mapEntitiesToDomain(it)
            }
        }
    }
}