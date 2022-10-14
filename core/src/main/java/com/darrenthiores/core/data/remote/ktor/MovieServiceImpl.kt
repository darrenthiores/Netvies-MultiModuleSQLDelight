package com.darrenthiores.core.data.remote.ktor

import com.darrenthiores.core.BuildConfig
import com.darrenthiores.core.model.data.MoviesResponse
import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.request.*
import timber.log.Timber

class MovieServiceImpl(
    private val client: HttpClient
): MovieService {
    override suspend fun getMovies(page: Long): MoviesResponse =
        try {
            client.get {
                url(HttpRoutes.POPULAR_MOVIES)
                parameter("api_key", BuildConfig.API_KEY) // make sure you have filled the api_key
                parameter("language", "en-US")
                parameter("page", page)
                parameter("region", "id")
            }
        } catch (e: RedirectResponseException) {
            // 3xx - responses
            Timber.e("Error: ${e.response.status.description}")
            MoviesResponse(emptyList())
        } catch (e: ClientRequestException) {
            // 4xx - responses
            Timber.e("Error: ${e.response.status.description}")
            MoviesResponse(emptyList())
        } catch (e: ServerResponseException) {
            // 5xx - responses
            Timber.e("Error: ${e.response.status.description}")
            MoviesResponse(emptyList())
        } catch (e: Exception) {
            // 6xx - responses
            Timber.e("Error: ${e.message}")
            MoviesResponse(emptyList())
        }

}