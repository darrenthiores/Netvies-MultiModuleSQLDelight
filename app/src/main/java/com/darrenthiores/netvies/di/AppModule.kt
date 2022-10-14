package com.darrenthiores.netvies.di

import com.darrenthiores.core.domain.MovieInteractor
import com.darrenthiores.core.domain.MovieUseCase
import com.darrenthiores.netvies.viewModel.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {

    factory<MovieUseCase> { MovieInteractor(get()) }

}

val viewModelModule = module {
    viewModel { MainViewModel(get()) }
}