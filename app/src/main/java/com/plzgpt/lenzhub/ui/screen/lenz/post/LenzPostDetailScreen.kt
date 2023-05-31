package com.plzgpt.lenzhub.ui.screen.lenz.post

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.plzgpt.lenzhub.ui.screen.lenz.viewmodel.PostUiState
import com.plzgpt.lenzhub.ui.theme.LHBackground
import com.plzgpt.lenzhub.ui.theme.LHBlack
import com.plzgpt.lenzhub.ui.theme.LHGray
import com.skydoves.landscapist.glide.GlideImage
import com.plzgpt.lenzhub.R
import com.plzgpt.lenzhub.ui.view.LongButton

@Preview(showBackground = true)
@Composable
fun LenzPostDetailScreen(
    uiState: PostUiState = PostUiState(),
    onNext: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Spacer(modifier = Modifier.height(18.dp))
        Row(
            modifier = Modifier
                .wrapContentSize()
                .padding(horizontal = 18.dp)
        ) {
            GlideImage(
                imageModel = uiState.profileImg,
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = uiState.userName,
                style = TextStyle(
                    color = LHBlack,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier
                    .align(CenterVertically)
            )
        }
        Spacer(modifier = Modifier.height(18.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 18.dp)
        ) {
            GlideImage(
                imageModel = uiState.beforeImg,
                modifier = Modifier
                    .weight(6f)
                    .aspectRatio(1f)
            )
            Spacer(modifier = Modifier.weight(1f))
            GlideImage(
                imageModel = uiState.afterImg,
                modifier = Modifier
                    .weight(6f)
                    .aspectRatio(1f)
            )
        }
        Spacer(modifier = Modifier.height(18.dp))
        Text(
            text = uiState.title,
            style = TextStyle(
                color = LHBlack,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier
                .wrapContentSize()
                .padding(horizontal = 18.dp)
        )
        Spacer(modifier = Modifier.height(18.dp))
        Text(
            text = uiState.description,
            style = TextStyle(
                color = LHGray,
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal
            ),
            modifier = Modifier
                .wrapContentSize()
                .padding(horizontal = 18.dp)
        )
        Spacer(modifier = Modifier.weight(1f))
        Spacer(modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(LHBackground))
        Spacer(modifier = Modifier.height(18.dp))
        Text(
            text = "카테고리",
            style = TextStyle(
                color = LHBlack,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier
                .wrapContentSize()
                .padding(horizontal = 18.dp)
        )
        Spacer(modifier = Modifier.height(18.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(horizontal = 18.dp)
        ) {
            Image(
                painter = painterResource(
                    when(uiState.category_name) {
                        "인물" -> R.drawable.ic_person
                        "동물" -> R.drawable.ic_animal
                        "풍경" -> R.drawable.ic_sight
                        "음식" -> R.drawable.ic_food
                        else -> 0
                    }
                ),
                contentDescription = null,
                modifier = Modifier
                    .size(24.dp)
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = uiState.category_name,
                style = TextStyle(
                    color = LHBlack,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal
                ),
                modifier = Modifier
                    .align(CenterVertically)
            )
        }
        Spacer(modifier = Modifier.height(18.dp))
        Spacer(modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(LHBackground))
        Spacer(modifier = Modifier.height(18.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(horizontal = 18.dp)
        ) {
            Image(
                painter = painterResource(
                    if(uiState.price == 0) R.drawable.ic_free
                    else R.drawable.ic_dollar
                ),
                contentDescription = null,
                modifier = Modifier
                    .size(24.dp)
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text =
                if(uiState.price == 0) "무료 렌즈입니다."
                else "유료 렌즈입니다.",
                style = TextStyle(
                    color = LHGray,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal
                ),
                modifier = Modifier
                    .align(CenterVertically)
            )
        }
        Spacer(modifier = Modifier.height(18.dp))
        Spacer(modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(LHBackground))
        Spacer(modifier = Modifier.height(18.dp))
        LongButton(
            text = if (uiState.price == 0) "저장하기" else "구매하기",
            onClick = {
                onNext()
            }
        )
        Spacer(modifier = Modifier.height(18.dp))
    }
}