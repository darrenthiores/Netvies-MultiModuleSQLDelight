package com.darrenthiores.netvies

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import com.darrenthiores.netvies.screen.MainScreen
import com.darrenthiores.netvies.ui.theme.NetviesTheme

@ExperimentalFoundationApi
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovieApp()
        }
    }
}

@ExperimentalFoundationApi
@Composable
private fun MovieApp() {
    NetviesTheme {
        MainScreen()
    }
}