package com.darrenthiores.core.model.data

import kotlinx.serialization.Serializable

@Serializable
data class MovieResponse(
    val id: Int,
    val title: String,
    val poster_path: String,
    val release_date: String,
    val vote_average: Double
)
