package com.plzgpt.lenzhub.ui.screen.lenz

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.plzgpt.lenzhub.R
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.plzgpt.lenzhub.ui.theme.LHBlack
import com.plzgpt.lenzhub.ui.theme.LHGray
import com.plzgpt.lenzhub.ui.view.LongButton
import com.plzgpt.lenzhub.util.bounceClick
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
@Preview(showBackground = true)
fun UploadScreen(

) {
    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        val scope = rememberCoroutineScope()
        val pagerState = rememberPagerState()
        val pages = listOf("포스트", "큐레이션")
        val context = LocalContext.current

        Text(
            text = "렌즈",
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 60.dp),
            style = TextStyle(
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp
            )
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            Modifier
                .wrapContentHeight()
                .align(Alignment.CenterHorizontally)
        ) {
            Text(
                text = "렌즈 만들기",
                fontSize = 20.sp,
                color =
                if (pagerState.currentPage == 0) LHBlack
                else LHGray,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .bounceClick {
                        scope.launch {
                            pagerState.animateScrollToPage(0)
                        }
                    })
            Spacer(Modifier.size(20.dp))
            Text(text = "렌즈 끼우기",
                fontSize = 20.sp,
                color =
                if (pagerState.currentPage == 1) LHBlack
                else LHGray,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.bounceClick {
                    scope.launch {
                        pagerState.animateScrollToPage(1)
                    }
                })
        }
        HorizontalPager(
            count = pages.size,
            state = pagerState,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) { page ->
            when (page) {
                0 -> {
                    Image(
                        painter = painterResource(id = R.drawable.ic_lenz_make),
                        contentDescription = null,
                        modifier = Modifier
                            .size(300.dp)
                    )
                }
                1 -> {
                    Image(
                        painter = painterResource(id = R.drawable.ic_lenz_apply),
                        contentDescription = null,
                        modifier = Modifier
                            .size(300.dp)
                    )
                }
            }
        }
        LongButton(
            text = "다음",
            onClick = {
                if (pagerState.currentPage == 0) {
                    context.startActivity(Intent(context, LenzMakeActivity::class.java))
                } else {
                    context.startActivity(Intent(context, LenzApplyActivity::class.java))
                }
            },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(18.dp))
    }
}