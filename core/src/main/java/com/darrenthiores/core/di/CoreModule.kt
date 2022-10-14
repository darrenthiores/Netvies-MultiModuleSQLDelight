package com.darrenthiores.core.di

import androidx.paging.ExperimentalPagingApi
import com.darrenthiores.core.MovieDatabase
import com.darrenthiores.core.data.local.LocalDataSource
import com.darrenthiores.core.data.local.source.MovieDataSource
import com.darrenthiores.core.data.local.source.MovieDataSourceImpl
import com.darrenthiores.core.data.local.source.MovieRemoteKeysDataSource
import com.darrenthiores.core.data.local.source.MovieRemoteKeysDataSourceImpl
import com.darrenthiores.core.data.remote.RemoteDataSource
import com.darrenthiores.core.data.remote.ktor.MovieService
import com.darrenthiores.core.data.remote.ktor.MovieServiceImpl
import com.darrenthiores.core.data.repository.IMovieRepository
import com.darrenthiores.core.data.repository.MovieRepository
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val databaseModule = module {

    single<SqlDriver> {
        AndroidSqliteDriver(
            schema = MovieDatabase.Schema,
            context = androidApplication(),
            name = "movie.db"
        )
    }

    single<MovieDataSource> { MovieDataSourceImpl(MovieDatabase(get())) }
    single<MovieRemoteKeysDataSource> { MovieRemoteKeysDataSourceImpl(MovieDatabase(get())) }

}

val networkModule = module {
    single {
        val json = kotlinx.serialization.json.Json {
            ignoreUnknownKeys = true
            isLenient = true
            encodeDefaults = false
        }
        HttpClient(Android) {
            install(Logging) {
                level = LogLevel.ALL
            }
            install(HttpTimeout) { // Timeout
                requestTimeoutMillis = 15000L
                connectTimeoutMillis = 15000L
                socketTimeoutMillis = 15000L
            }
            install(JsonFeature) {
                serializer = KotlinxSerializer(json)
            }
        }
    }
    single<MovieService> { MovieServiceImpl(get()) }
}

@ExperimentalPagingApi
val repositoryModule = module {

    single { RemoteDataSource(get()) }
    single { LocalDataSource(MovieDatabase(get()), get(), get()) }

    single<IMovieRepository> { MovieRepository(get(), get()) }

}