package com.plzgpt.lenzhub.ui.screen.lenz.post

import android.annotation.SuppressLint
import android.content.Intent
import android.util.Log
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
import androidx.compose.ui.platform.LocalContext
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
import com.plzgpt.lenzhub.ui.screen.profile.ProfileActivity
import com.plzgpt.lenzhub.ui.view.LongButton
import com.plzgpt.lenzhub.util.bounceClick
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun LenzPostDetailScreen(
    uiStateFlow: StateFlow<PostUiState>, // StateFlow로 변경
    onNext: () -> Unit = {}
) {
    val uiState = uiStateFlow.value // 최신 uiState 값 가져오기
    var profile = arrayOf(
        "https://techrecipe.co.kr/wp-content/uploads/2022/08/220819_Beautiful-Landscapes_ai_0001.jpg",
        "https://i.namu.wiki/i/qFWfOHBd0mx7NmNquwtaSbUjnPumXpk5oi1jxNKpWUsv_eGJe44xm9AePkbhQ6hIxTjMtroFaOFPbhBy0MSbNQ.webp",
        "https://src.hidoc.co.kr/image/lib/2022/5/12/1652337370806_0.jpg",
        "https://img.danawa.com/prod_img/500000/869/844/img/2844869_1.jpg?_v=20210325103140",
        "https://cloudfront-ap-northeast-1.images.arcpublishing.com/chosunbiz/T76RHKX27GOS5BHD6LCD5W6DNQ.jpg")



    Log.d("uiState77", uiState.toString())

    val context = LocalContext.current

    Log.d("uiState7337", uiState.toString())
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
                .bounceClick {
                    val intent = Intent(context, ProfileActivity::class.java)
                    Log.d("uiState7887", uiState.userIdx.toString())
                    intent.putExtra("profileId", uiState.userIdx)
                    context.startActivity(intent)
                     },
        ) {
            GlideImage(
                imageModel = if(uiState.profileImg == "") profile[uiState.userIdx%profile.size] else uiState.profileImg,
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