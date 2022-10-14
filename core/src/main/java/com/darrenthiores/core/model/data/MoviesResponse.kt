package com.darrenthiores.core.model.data

import kotlinx.serialization.Serializable

@Serializable
data class MoviesResponse(
    val results: List<MovieResponse>
)
