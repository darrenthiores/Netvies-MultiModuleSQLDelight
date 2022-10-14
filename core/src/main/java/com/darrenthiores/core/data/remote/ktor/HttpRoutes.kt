package com.darrenthiores.core.data.remote.ktor

import com.darrenthiores.core.BuildConfig

object HttpRoutes {

    private const val BASE_URL = BuildConfig.BASE_URL
    const val POPULAR_MOVIES = "$BASE_URL/3/movie/popular"

}