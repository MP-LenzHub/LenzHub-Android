package com.plzgpt.lenzhub.ui.screen.upload

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.plzgpt.lenzhub.ui.view.LongButton

@Composable
fun LenzMakeResult(
    onNext: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Spacer(modifier = Modifier.weight(1f))
        LongButton(
            text = "다음",
            onClick = {
                onNext()
            }
        )
        Spacer(modifier = Modifier.height(18.dp))
    }
}