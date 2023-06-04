package com.plzgpt.lenzhub.ui.screen.search

import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import com.plzgpt.lenzhub.R
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.request.RequestOptions
import com.plzgpt.lenzhub.api.dto.GetSearchCategoryPost
import com.plzgpt.lenzhub.ui.screen.lenz.post.LenzPostActivity
import com.plzgpt.lenzhub.ui.theme.LHBlack
import com.plzgpt.lenzhub.ui.theme.LHGray
import com.plzgpt.lenzhub.ui.theme.randomImage
import com.plzgpt.lenzhub.util.ShowProgressBar
import com.plzgpt.lenzhub.util.bounceClick
import com.skydoves.landscapist.glide.GlideImage


//포스트, 큐레이션에 표시되는 카드들로 데이터 형식 알려주면 그때 넣겠삼삼
@Composable
fun  SearchCategoryCard(
    postData: GetSearchCategoryPost


) {
    val mContext = LocalContext.current
    val likeCnt = remember { mutableStateOf(postData.likedCount) }
    val context = LocalContext.current

    val launcher = rememberLauncherForActivityResult(contract =
    ActivityResultContracts.StartIntentSenderForResult()) {
//        postList.value = GetSearchPost()
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .bounceClick {
                val intent = Intent(context, LenzPostActivity::class.java)
                intent.putExtra("postId", postData.id)
                context.startActivity(intent)
                         },
//                val intent = Intent(mContext, PostContentActivity::class.java)
//                val bundle = Bundle()
//
//                bundle.putInt("board_id", postData.id)
//                bundle.putBoolean("run", true)
//
//                intent.putExtras(bundle)
//
//                val pendIntent: PendingIntent
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
//                    pendIntent = PendingIntent
//                        .getActivity(
//                            mContext, 0,
//                            intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent . FLAG_MUTABLE
//                        )
//
//                } else {
//                    pendIntent = PendingIntent
//                        .getActivity(
//                            mContext, 0,
//                            intent, PendingIntent.FLAG_UPDATE_CURRENT
//                        )
//                }
//
//                launcher.launch(
//                    IntentSenderRequest
//                        .Builder(pendIntent)
//                        .build()
//                )
        verticalAlignment = Alignment.CenterVertically
    ) {
        Card(
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
        ) {
            //타이틀 이미지
            GlideImage( // CoilImage, FrescoImage
                imageModel = postData.afterImg,
                modifier = Modifier
                    .size(width = 110.dp, height = 110.dp)
                    .clip(RoundedCornerShape(15.dp)),
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

        }
        Spacer(modifier = Modifier.width(18.dp))
        Column {
            //타이틀
            Text(
                modifier = Modifier.width(230.dp),
                text = postData.title,
                style = TextStyle(color = LHBlack,
                    fontWeight = FontWeight(500),
                    fontSize = 16.sp),
                //넘치면 ....으로 표시해주는놈
                overflow = TextOverflow.Ellipsis,
                maxLines = 2
            )

            Spacer(modifier = Modifier.height(7.dp))

            //시간
            val timeLine = postData.date.split("T")[0].split("-")
            Text(text = "${timeLine[0]}년 ${timeLine[1]}월 ${timeLine[2]}일",
                style = TextStyle(color = LHGray,
                    fontWeight = FontWeight(400),fontSize = 12.sp)
            )

            Spacer(modifier = Modifier.height(7.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                GlideImage(
                    imageModel = randomImage[postData.userIdx % randomImage.size],
                    modifier = Modifier
                        .size(width = 20.dp, height = 20.dp)
                        .aspectRatio(1f)
                        .clip(CircleShape),
                    loading = {
                        ShowProgressBar()
                    },
                    // shows an error text if fail to load an image.
                    failure = {
                        Text(text = "image request failed.")
                    },
                    requestOptions = {
                        RequestOptions()
                            .override(128,128)
                    }
                )

                Spacer(modifier = Modifier.width(10.dp))
                //작성자
                Text(
                    modifier = Modifier
                        .padding(bottom = 3.dp),
                    text = postData.userName,
                    style = TextStyle(color = Color(0xFF252525).copy(alpha = 0.8f),
                        fontWeight = FontWeight(400),fontSize = 12.sp)
                )

            }
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.weight(1f))
                if(postData.price > 0) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_dollar),
                        contentDescription = null,
                        modifier = Modifier
                            .size(width = 22.dp, height = 22.dp)
                            .padding(end = 6.dp)
                    )
                }
                Image(
                    painter = painterResource(id = R.drawable.ic_heart_mini),
                    contentDescription = null,
                    modifier = Modifier
                        .size(width = 15.dp, height = 15.dp)
                )

                Text(
                    modifier = Modifier
                        .padding(start = 5.dp),
                    text = "${likeCnt.value}",
                    style = TextStyle(color = LHGray,
                        fontWeight = FontWeight(400),fontSize = 12.sp)
                )

            }
        }

    }

    Spacer(modifier = Modifier.height(18.dp))

}