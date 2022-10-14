package com.darrenthiores.netvies.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.paging.compose.collectAsLazyPagingItems
import com.darrenthiores.netvies.component.MovieList
import com.darrenthiores.netvies.viewModel.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel

@ExperimentalFoundationApi
@Composable
fun MainScreen(
    modifier: Modifier = Modifier
) {
    val viewModel = getViewModel<MainViewModel>()
    val coroutineScope = rememberCoroutineScope()
    val listState = rememberLazyGridState()
    val showButton by remember {
        derivedStateOf {
            listState.firstVisibleItemIndex > 0
        }
    }
    MainScreen(
        modifier = modifier,
        viewModel = viewModel,
        coroutineScope = coroutineScope,
        listState = listState,
        showButton = showButton
    )
}

@ExperimentalFoundationApi
@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel,
    coroutineScope: CoroutineScope,
    listState: LazyGridState,
    showButton: Boolean
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Netvlies",
                        style = MaterialTheme.typography.h4
                    )
                }
            )
        },
        floatingActionButton = {
            if(showButton) {
                FloatingActionButton(
                    onClick = {
                        coroutineScope.launch {
                            listState.animateScrollToItem(0)
                        }
                    },
                    backgroundColor = MaterialTheme.colors.primary
                ) {
                    Text(text = "Up!")
                }
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) { paddingValues ->
        val movies = viewModel.movies.collectAsLazyPagingItems()
        MovieList(
            modifier = modifier
                .padding(paddingValues)
                .semantics { contentDescription = "Main Screen" },
            movies = movies,
            listState = listState
        )
    }
}