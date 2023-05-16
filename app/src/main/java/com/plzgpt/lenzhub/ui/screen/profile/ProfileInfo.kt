package com.plzgpt.lenzhub.ui.screen.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.request.RequestOptions
import com.plzgpt.lenzhub.R
import com.plzgpt.lenzhub.api.dto.GetSearchLikeUser
import com.plzgpt.lenzhub.util.ShowProgressBar
import com.plzgpt.lenzhub.util.bounceClick
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun ProfileInfo(profileData : GetSearchLikeUser){

    Row(modifier = Modifier.fillMaxWidth().bounceClick {  }) {
        //Box로 바꿩
        GlideImage( // CoilImage, FrescoImage
            imageModel = profileData.user_profile_img,
            modifier = Modifier
                .size(width = 50.dp, height = 50.dp)
                .clip(RoundedCornerShape(50.dp)),
            contentScale = ContentScale.Crop,
            // shows an indicator while loading an image.
            loading = {
                ShowProgressBar()
            },
            // shows an error text if fail to load an image.
            failure = {
                Text(text = "image request failed.")
            },
            requestOptions = {
                RequestOptions()
                    .override(256,256)
            }
        )
        Spacer(Modifier.width(18.dp))
        Column {
            Row() {
                Text(
                    "${profileData.user_name}",
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                )
                // 프리미엄 요금제냐는 뜻 - 구독제를 쓰냐는 뜻
                if (profileData.free) {
                    Spacer(Modifier.width(3.dp))
                    Surface(
                        modifier = Modifier.size(20.dp),
                        shape = CircleShape
                    ) {
                        Image(painterResource(id = R.drawable.ic_check_on), contentDescription = "")
                    }
                }


            }
            Spacer(modifier = Modifier.height(7.dp))
            Text(
                "필터:${profileData.filter}, 좋아요:${profileData.likes}",
                style = TextStyle(
                    fontWeight = FontWeight.Normal,
                    fontSize = 11.sp,
                    color = Color.Gray
                )
            )
        }
    }
}