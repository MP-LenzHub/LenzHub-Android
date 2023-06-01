package com.plzgpt.lenzhub.ui.screen.main

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bumptech.glide.request.RequestOptions
import com.plzgpt.lenzhub.ApplicationClass
import com.plzgpt.lenzhub.ApplicationClass.Companion.clientId
import com.plzgpt.lenzhub.R
import com.plzgpt.lenzhub.api.dto.GetSearchCategoryPost
import com.plzgpt.lenzhub.ui.screen.lenz.post.LenzPostActivity
import com.plzgpt.lenzhub.ui.screen.lenz.viewmodel.HomeViewModel
import com.plzgpt.lenzhub.ui.screen.lenz.viewmodel.PostUiState
import com.plzgpt.lenzhub.ui.theme.LHDivider
import com.plzgpt.lenzhub.ui.theme.LHMainBackground
import com.plzgpt.lenzhub.util.PostHeartCard
import com.plzgpt.lenzhub.util.ShowProgressBar
import com.plzgpt.lenzhub.util.bounceClick
import com.skydoves.landscapist.glide.GlideImage

@Preview
@Composable
fun MainScreen(
    viewModel: HomeViewModel = viewModel(),
    ) {
    val userId = ApplicationClass.sharedPreferences.getInt(clientId, 0)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(LHMainBackground)
            .padding(start = 12.dp, end = 12.dp, top = 12.dp, bottom = 0.dp)

    ) {
        PostList(viewModel, userId)
    }
}

@Composable
fun PostList(viewModel: HomeViewModel, userId:Int) {
    val page = 0
    val size = 10
    viewModel.getAllPostState(page, size)
    val posts by viewModel.allPostState.collectAsState()

    val fontFamily = FontFamily(
        Font(R.font.seoul_hangang, FontWeight.Light),
        Font(R.font.seoul_hangang_bl, FontWeight.Bold),
    )


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(12.dp))
        Text(text = "LenzHub", fontFamily = fontFamily, fontSize = 24.sp, style = TextStyle(fontWeight = FontWeight.Bold))
        Spacer(modifier = Modifier.height(12.dp))



        val scrollState = rememberLazyListState()
        val coroutineScope = rememberCoroutineScope()


        LazyColumn(
            state = scrollState,
            contentPadding = PaddingValues(horizontal = 0.dp, vertical = 18.dp),
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {

            items(items = posts.postList, key ={it.id}){
                PostItem(index = it.id, post = it)
            }
        }
    }
}

@Composable
fun ProfileInfo(userIdx : Int = 0, mode : Int = 0, userName:String = "test", userImage:String = "", grade: String = "Basic") {

    var cashman = false
    if (grade != "Basic") {
        cashman = true
    }
    val myId = ApplicationClass.sharedPreferences.getInt(ApplicationClass.clientId, 0)


//Box로 바꿩
    Surface(
        modifier = if (mode == 0) Modifier.size(24.dp) else Modifier.size(50.dp),
        color = MaterialTheme.colors.onSurface.copy(alpha = 0.2f),
        shape = CircleShape
    ) {
        GlideImage(
            imageModel = userImage,
            contentDescription = "",
            modifier = Modifier.size(20.dp)
        )
    }
    Spacer(if(mode==0) Modifier.width(8.dp) else Modifier.width(18.dp))
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            userName,
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = if (mode == 0) 17.sp else 18.sp
            )
        )

        // 비슷한 구조라서 메인이 post의 프로필인지, 프로필screen의 프로필인지에 따라 크기 변경
        if (mode != 0 || cashman) {
            // 프리미엄 요금제냐는 뜻 - 구독제를 쓰냐는 뜻
            if (grade != "Basic") {
                Spacer(Modifier.width(3.dp))
                Surface(
                    modifier = Modifier.size(20.dp),
                    shape = CircleShape
                ) {
                    Image(painterResource(id = R.drawable.ic_check_on), contentDescription = "")
                }
            }
        }
    }

}

@Composable
fun PostItem(index: Int, post: GetSearchCategoryPost){

    val isLiked = remember { mutableStateOf(false) } // 좋아요 했는지 여부
    val userName = post.userName
    val userImage = post.profileImg
    val context = LocalContext.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .bounceClick {
                val intent = Intent(context, LenzPostActivity::class.java)
                intent.putExtra("postId", post.id)
                context.startActivity(intent)
                isLiked.value = !isLiked.value },
        elevation = 4.dp,
        shape = RoundedCornerShape(20.dp)
    ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 17.dp, start = 17.dp, end = 17.dp, bottom = 7.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row() {
                        ProfileInfo(
                            mode = 0,
                            userName = userName,
                            userImage = userImage,
                            grade = "Basic"
                        )
                    }
                    PostHeartCard(modifier = Modifier, heartState = isLiked)
                }
                Divider(color = LHDivider, thickness = 1.dp)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(188.dp)
                        .padding(17.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Surface(
                        modifier = Modifier
                            .size(150.dp),
                        color = MaterialTheme.colors.onSurface.copy(alpha = 0.2f),
                    ) {
                        GlideImage(
                            imageModel =
                            if (post.beforeImg == null)
                                R.drawable.ic_launcher_foreground
                            else
                                post.beforeImg,
                            modifier = Modifier
                                .aspectRatio(1f),
                            loading = {
                                ShowProgressBar()
                            },
                            // shows an error text if fail to load an image.
                            failure = {
                                Text(text = "image request failed.")
                            },
                            requestOptions = {
                                RequestOptions()
                                    .override(128, 128)
                            }
                        )
                    }

                    Surface(
                        modifier = Modifier
                            .size(150.dp),
                        color = MaterialTheme.colors.onSurface.copy(alpha = 0.2f),
                    ) {
                        GlideImage(
                            imageModel =
                            if (post.afterImg == null)
                                R.drawable.ic_launcher_foreground
                            else
                                post.afterImg,
                            modifier = Modifier
                                .aspectRatio(1f),
                            loading = {
                                ShowProgressBar()
                            },
                            // shows an error text if fail to load an image.
                            failure = {
                                Text(text = "image request failed.")
                            },
                            requestOptions = {
                                RequestOptions()
                                    .override(128, 128)
                            }
                        )
                    }
                }

                Divider(color = LHDivider, thickness = 1.dp)

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 15.dp, bottom = 15.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = post.title,
                            modifier = Modifier.padding(start = 17.dp),
                            fontWeight = FontWeight.Bold,
                            fontSize = 19.sp
                        )
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(), verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = post.category_name,
                            modifier = Modifier.padding(start = 17.dp),
                            fontSize = 14.sp,
                            color = Color.Gray
                        )
                        Row {
                            if (post.price > 0) {
                                Image(
                                    painterResource(id = R.drawable.ic_dollar),
                                    contentDescription = "",
                                    Modifier.size(19.dp),
                                )

                                Spacer(modifier = Modifier.width(6.dp))
                            }
                            IconButton(
                                onClick = { /*TODO*/ },
                                Modifier
                                    .size(17.dp)
                            ) {
                                Icon(
                                    painter = painterResource(
                                        id = R.drawable.ic_heart_mini
                                    ), contentDescription = "",
                                    Modifier
                                        .size(17.dp),
                                    tint = Color(0xFFB6BFBA)
                                )
                            }
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = post.likedCount.toString(), Modifier
                                    .padding(end = 15.dp), fontSize = 13.sp, color = Color.Gray
                            )
                        }
                    }
                }
                Spacer(Modifier.width(17.dp))
            }
    }
}