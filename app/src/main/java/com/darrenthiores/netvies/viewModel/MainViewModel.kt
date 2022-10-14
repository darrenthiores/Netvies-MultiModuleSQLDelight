package com.darrenthiores.netvies.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.darrenthiores.core.domain.MovieUseCase
import com.darrenthiores.core.model.presenter.Movie
import com.darrenthiores.core.utils.DataMapper
import kotlinx.coroutines.flow.*

class MainViewModel(
    movieUseCase: MovieUseCase
): ViewModel() {

    private val _movies: Flow<PagingData<Movie>> =
        movieUseCase.getMovies().map { pagingData ->
            pagingData.map {
                DataMapper.mapDomainToPresenter(it)
            }
        }.cachedIn(viewModelScope)

    val movies: Flow<PagingData<Movie>>
        get() = _movies
}