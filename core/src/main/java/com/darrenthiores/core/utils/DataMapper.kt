package com.darrenthiores.core.utils

import com.darrenthiores.core.model.data.MovieResponse
import com.darrenthiores.core.model.domain.MovieDomain
import com.darrenthiores.core.model.presenter.Movie
import netviescore.moviedb.MovieEntity

object DataMapper {

    fun mapResponsesToDomain(input: MovieResponse): MovieDomain =
        MovieDomain(
            input.id,
            input.title,
            input.poster_path,
            input.release_date,
            input.vote_average
        )

    fun mapDomainToPresenter(input: MovieDomain): Movie =
        Movie(
            input.id,
            input.title,
            input.poster,
            input.date,
            input.vote
        )

    fun mapEntitiesToDomain(input: MovieEntity): MovieDomain =
        MovieDomain(
            input.id,
            input.title,
            input.poster_path,
            input.release_date,
            input.vote_average
        )

    fun mapResponsesToEntities(input: MovieResponse): MovieEntity =
        MovieEntity(
            remoteId = 0,
            id = input.id,
            title = input.title,
            poster_path = input.poster_path,
            release_date = input.release_date,
            vote_average = input.vote_average
        )
}