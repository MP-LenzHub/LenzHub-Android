package com.plzgpt.lenzhub.util

import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.plzgpt.lenzhub.R

@Composable
fun PostHeartCard(
//    boardId: Int,
    modifier: Modifier,
    heartState: MutableState<Boolean>,
//    likeNum : MutableState<Int>?
) {
    Icon(
        modifier = modifier
        .bounceClick {
            heartState.value = !heartState.value
        }
        ,painter = painterResource(
            id = if (heartState.value) R.drawable.ic_heart_solid
            else R.drawable.ic_heart_line
        ),
        contentDescription = "좋아요",
        tint =
        if (heartState.value) Color(0xFFFED2DA)
        else Color(0xFFC5C9D1)

    )
}