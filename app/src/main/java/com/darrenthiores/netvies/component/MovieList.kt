package com.darrenthiores.netvies.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.darrenthiores.core.model.presenter.Movie
import timber.log.Timber

@ExperimentalFoundationApi
public fun <T: Any> LazyGridScope.items(
    lazyPagingItems: LazyPagingItems<T>,
    itemContent: @Composable LazyGridItemScope.(value: T?) -> Unit
) {
    items(lazyPagingItems.itemCount) { index ->
        itemContent(lazyPagingItems[index])
    }
}

@ExperimentalFoundationApi
@Composable
fun MovieList(
    modifier: Modifier = Modifier,
    movies: LazyPagingItems<Movie>,
    listState: LazyGridState
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(180.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
            .padding(8.dp)
            .fillMaxSize(),
        state = listState
    ) {
        items(movies) { movie ->
            MovieItem(
                modifier = Modifier.semantics {
                    contentDescription = "${movie?.title} Movie"
                },
                movie = movie ?: Movie(0, "NULL", "", "NULL", 0.0)
            )
        }

        movies.apply {
            when {
                loadState.refresh is LoadState.Loading -> {
                    repeat(6) {
                        item {
                            LoadingMovie()
                        }
                    }
                }
                loadState.append is LoadState.Loading -> {
                    repeat(6) {
                        item {
                            LoadingMovie()
                        }
                    }
                }
                loadState.refresh is LoadState.Error -> {
                    val e = movies.loadState.refresh as LoadState.Error
                    Timber.e(e.error)
                }
                loadState.append is LoadState.Error -> {
                    val e = movies.loadState.append as LoadState.Error
                    Timber.e(e.error)
                }
            }
        }
    }
}