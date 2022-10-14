package com.darrenthiores.netvies

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.darrenthiores.netvies.screen.MainScreen
import com.darrenthiores.netvies.ui.theme.NetviesTheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.*
import kotlin.concurrent.schedule

class MainScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @ExperimentalFoundationApi
    @Before
    fun main_screen_setup() {

        composeTestRule.setContent {
            NetviesTheme {
                MainScreen()
            }
        }

    }

    @Test
    fun app_bar_is_showing() {
        composeTestRule.onNodeWithText("Netvlies", useUnmergedTree = true)
            .assertIsDisplayed()
    }

    @Test
    fun main_screen_is_showing() {
        composeTestRule.onNodeWithContentDescription("Main Screen")
            .assertIsDisplayed()
    }

    @Test
    fun item_of_list_is_showing() {
        asyncTimer() // to wait the item fetched

        val firstMovie = "The Batman" // first movie might change, so make sure use the latest first movie
        composeTestRule.onNodeWithContentDescription("$firstMovie Movie", useUnmergedTree = true)
            .assertIsDisplayed()
    }

    private fun asyncTimer (delay: Long = 5000) {
        AsyncTimer.start (delay)
        composeTestRule.waitUntil (
            condition = {AsyncTimer.expired},
            timeoutMillis = delay + 1000
        )
    }

    object AsyncTimer {
        var expired = false
        fun start(delay: Long = 1000){
            expired = false
            Timer().schedule(delay) {
                expired = true
            }
        }
    }

}