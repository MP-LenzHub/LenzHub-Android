package com.plzgpt.lenzhub.ui.screen.main

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

@Composable
fun MainScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)

    ) {
        Text(
            text = "Home",
            modifier = Modifier
                .align(Alignment.Center),
            style = TextStyle(
                color = Color.Black,
                fontWeight = FontWeight(400),
                fontSize = 70.sp
            )
        )
    }
}