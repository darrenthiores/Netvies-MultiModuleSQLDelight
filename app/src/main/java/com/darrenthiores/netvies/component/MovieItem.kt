package com.darrenthiores.netvies.component

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.darrenthiores.core.model.presenter.Movie
import com.darrenthiores.netvies.ui.theme.NetviesTheme

@Composable
fun MovieItem(
    modifier: Modifier = Modifier,
    movie: Movie
) {
    Surface(
        modifier = modifier
            .width(180.dp)
            .height(330.dp),
        elevation = 1.dp
    ) {
        Column {
            Image(
                painter = rememberImagePainter(data = "https://image.tmdb.org/t/p/original/${movie.poster}"),
                contentDescription = "${movie.title} poster",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .padding(vertical = 5.dp),
                alignment = Alignment.Center
            )
            Text(
                text = movie.title.uppercase(),
                style = MaterialTheme.typography.body2,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 5.dp)
                    .padding(horizontal = 16.dp),
                maxLines = 1
            )
            Row(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = movie.date,
                    modifier = Modifier.weight(1f),
                    style = MaterialTheme.typography.body2
                )
                Text(
                    text = movie.vote.toString(),
                    style = MaterialTheme.typography.body2
                )
            }
        }
    }
}

@Composable
fun LoadingMovie() {
    val infiniteTransition = rememberInfiniteTransition()
    val alpha by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = InfiniteRepeatableSpec(
            animation = keyframes {
                durationMillis = 1000
                0.7f at 500
            },
            repeatMode = RepeatMode.Reverse
        )
    )

    LoadingMovie(alpha = alpha)

}

@Composable
fun LoadingMovie(
    modifier: Modifier = Modifier,
    alpha: Float
) {
    Surface(
        modifier = modifier
            .width(180.dp)
            .height(330.dp),
        elevation = 1.dp
    ) {
        Column{
            Box(
                modifier = Modifier
                    .width(170.dp)
                    .height(250.dp)
                    .padding(vertical = 5.dp)
                    .background(Color.LightGray.copy(alpha = alpha))
                    .align(Alignment.CenterHorizontally)
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 5.dp)
                    .padding(horizontal = 16.dp)
                    .background(Color.LightGray.copy(alpha = alpha))
                    .weight(0.25f)
            )
            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .weight(0.6f)
            ) {
                Box(
                    modifier = Modifier
                        .size(14.dp)
                        .weight(1f)
                        .background(Color.LightGray.copy(alpha = alpha))
                )
                Spacer(modifier = Modifier.width(24.dp))
                Box(
                    modifier = Modifier
                        .size(14.dp)
                        .width(14.dp)
                        .background(Color.LightGray.copy(alpha = alpha))
                )
            }
        }
    }
}

@Preview
@Composable
fun MovieItemPreview() {
    NetviesTheme {
        MovieItem(movie = Movie(1, "Test Movie", "", "23/5/2022", 8.0))
    }
}

@Preview
@Composable
fun MovieLoadingPreview() {
    NetviesTheme {
        LoadingMovie()
    }
}