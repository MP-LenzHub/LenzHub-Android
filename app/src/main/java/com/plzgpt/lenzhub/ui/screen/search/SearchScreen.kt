package com.plzgpt.lenzhub.ui.screen.search

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.plzgpt.lenzhub.ui.route.NavigationGraphSearch

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SearchScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)

    ) {
        SearchNavScreen()
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SearchNavScreen() {
    val navController = rememberNavController()
    NavigationGraphSearch(navController = navController)
}