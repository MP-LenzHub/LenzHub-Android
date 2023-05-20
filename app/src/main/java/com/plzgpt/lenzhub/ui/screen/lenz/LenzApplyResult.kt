package com.plzgpt.lenzhub.ui.screen.lenz

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.plzgpt.lenzhub.R
import com.plzgpt.lenzhub.ui.theme.LHBlack
import com.plzgpt.lenzhub.ui.theme.LHGray
import com.plzgpt.lenzhub.ui.view.LongButton
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun LenzApplyResult(
    onNext: () -> Unit = {},
    resultPhoto: Bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888)
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Spacer(modifier = Modifier.height(100.dp))
        GlideImage(
            imageModel = resultPhoto,
            contentDescription = null,
            modifier = Modifier
                .size(300.dp)
                .align(CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(40.dp))
        Text(
            text = "저장 성공!",
            style = TextStyle(
                color = LHBlack,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.align(CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(18.dp))
        Text(
            text = "갤러리에서 확인해보세요.",
            style = TextStyle(
                color = LHGray,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.weight(1f))
        LongButton(
            text = "메인으로",
            onClick = {
                onNext()
            }
        )
        Spacer(modifier = Modifier.height(18.dp))
    }
}