package com.plzgpt.lenzhub.ui.screen.main

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.lifecycle.viewmodel.compose.viewModel
import com.plzgpt.lenzhub.ApplicationClass
import com.plzgpt.lenzhub.ApplicationClass.Companion.clientId
import com.plzgpt.lenzhub.R
import com.plzgpt.lenzhub.ui.screen.lenz.post.LenzPostActivity
import com.plzgpt.lenzhub.ui.screen.lenz.viewmodel.HomeViewModel
import com.plzgpt.lenzhub.ui.screen.lenz.viewmodel.PostUiState
import com.plzgpt.lenzhub.ui.theme.LHDivider
import com.plzgpt.lenzhub.ui.theme.LHMainBackground
import com.plzgpt.lenzhub.util.PostHeartCard
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
    val postAllState by viewModel.allPostState.collectAsState()
    val postList = postAllState.postList


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(12.dp))
        Text(text = "LenzHub", fontSize = 24.sp, style = TextStyle(fontWeight = FontWeight.Bold))
        Spacer(modifier = Modifier.height(12.dp))



        val scrollState = rememberLazyListState()
        val coroutineScope = rememberCoroutineScope()


        //받아와
        // list 넘겨줘
        LazyColumn(
            state = scrollState,
            contentPadding = PaddingValues(horizontal = 0.dp, vertical = 18.dp),
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {

            items(postList.size) { index ->
                PostItem(index = index, post = postList[index])
            }
        }
    }
}

@Composable
fun ProfileInfo(index:Int, mode : Int = 0, userName:String = "test", userImage:String = "", price: Int = 0) {


//Box로 바꿩
    Surface(
        modifier = if (mode == 0) Modifier.size(24.dp) else Modifier.size(50.dp),
        color = MaterialTheme.colors.onSurface.copy(alpha = 0.2f),
        shape = CircleShape
    ) {
        Image(painterResource(id = R.drawable.ic_food), contentDescription = "")
    }
    Spacer(if(mode==0) Modifier.width(8.dp) else Modifier.width(18.dp))
    Column() {
        Text(userName,
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = if (mode == 0) 17.sp else 18.sp
            )
        )
    }

    // 비슷한 구조라서 메인이 post의 프로필인지, 프로필screen의 프로필인지에 따라 크기 변경
    if(mode != 0){
        // 프리미엄 요금제냐는 뜻 - 구독제를 쓰냐는 뜻
        if(price != 0) {
            Spacer(Modifier.width(3.dp))
            Surface(
                modifier = Modifier.size(20.dp),
                shape = CircleShape
            ) {
                GlideImage(
                    imageModel = userImage,
                    contentDescription = "",
                    modifier = Modifier.size(20.dp)
                )
            }
        }

    }
}

@Composable
fun PostItem(index: Int, post: PostUiState){

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
                    .padding(top = 17.dp, start = 17.dp, end = 17.dp, bottom = 7.dp)
                ,
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row() {
                    ProfileInfo(index = index, mode = 0, userName = userName, userImage = userImage, price = post.price)
                }
                PostHeartCard(modifier = Modifier, heartState = isLiked)
            }
            Divider(color = LHDivider, thickness = 1.dp)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(188.dp)
                    .padding(17.dp)
                ,
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Surface(
                    modifier = Modifier
                        .size(150.dp),
                    color = MaterialTheme.colors.onSurface.copy(alpha = 0.2f),
                ) {
                    GlideImage(imageModel = post.beforeImg.toUri(), contentDescription = "original")
                }

                Surface(
                    modifier = Modifier
                        .size(150.dp),
                    color = MaterialTheme.colors.onSurface.copy(alpha = 0.2f),
                ) {
                    GlideImage(imageModel = post.afterImg.toUri(), contentDescription = "original")
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
                        IconButton(onClick = { /*TODO*/ },
                            Modifier
                                .size(17.dp)) {
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
                        Text(text = post.likedCount.toString(), Modifier
                            .padding(end = 15.dp),fontSize = 13.sp, color = Color.Gray
                        )
                    }
                }
            }
            Spacer(Modifier.width(17.dp))
        }
    }
}