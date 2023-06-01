package com.plzgpt.lenzhub.ui.screen.lenz.post

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.plzgpt.lenzhub.ui.theme.LHBlack
import com.plzgpt.lenzhub.ui.theme.LHGray
import com.plzgpt.lenzhub.ui.view.LongButton
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun LenzSaveScreen(
    title: String = "",
    beforeImg: String = "",
    afterImg: String = "",
    onNext: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Spacer(modifier = Modifier.height(18.dp))
        Text(
            text = "저장 완료!",
            style = TextStyle(
                color = LHBlack,
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier
                .align(CenterHorizontally)
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = title,
            style = TextStyle(
                color = LHBlack,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier
                .align(CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(18.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 18.dp)
        ) {
            GlideImage(
                imageModel = beforeImg,
                modifier = Modifier
                    .weight(6f)
                    .aspectRatio(1f)
            )
            Spacer(modifier = Modifier.weight(1f))
            GlideImage(
                imageModel = afterImg,
                modifier = Modifier
                    .weight(6f)
                    .aspectRatio(1f)
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = "지금 바로 사용해보세요!",
            style = TextStyle(
                color = LHGray,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier
                .align(CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(18.dp))
        LongButton(
            text = "확인",
            onClick = {
                onNext()
            }
        )
        Spacer(modifier = Modifier.height(18.dp))
    }
}