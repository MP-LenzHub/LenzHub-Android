package com.plzgpt.lenzhub.ui.screen.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.plzgpt.lenzhub.R
import com.plzgpt.lenzhub.ui.theme.LHDivider
import com.plzgpt.lenzhub.ui.theme.LHMainBackground
import com.plzgpt.lenzhub.util.PostHeartCard
import com.plzgpt.lenzhub.util.bounceClick

@Preview
@Composable
fun MainScreen(

//LenzMakeScreen 참고

) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(LHMainBackground)
            .padding(12.dp)

    ) {
        PostList()
    }
}

@Composable
fun PostList(){
    val listSize = 10
    val scrollState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(12.dp))
        Text(text = "LenzHub", fontSize = 24.sp)
        Spacer(modifier = Modifier.height(12.dp))


        val listSize = 10
        val scrollState = rememberLazyListState()
        val coroutineScope = rememberCoroutineScope()


        //받아와
        // list 넘겨줘
        LazyColumn(
            state = scrollState,
            contentPadding = PaddingValues(horizontal = 0.dp, vertical = 18.dp),
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {

            items(listSize) { index ->
                PostItem(index = index)
            }
        }
    }
}


//@Composable
//fun PostList(){
//    val listSize = 10
//    val scrollState = rememberLazyListState()
//    val coroutineScope = rememberCoroutineScope()
//
//
//    LazyColumn(state = scrollState,
//            contentPadding = PaddingValues(horizontal = 0.dp, vertical = 18.dp),
//            verticalArrangement = Arrangement.spacedBy(18.dp)
//        ){
//            items(listSize){ index ->
//                PostItem(index = index)
//            }
//        }
//
//}

@Composable
fun ProfileInfo(index:Int, mode : Int = 0){

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
        Text(
            "#$index eunseob",
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = if (mode == 0) 17.sp else 18.sp
            )
        )
    }

    // 비슷한 구조라서 메인이 post의 프로필인지, 프로필screen의 프로필인지에 따라 크기 변경
    if(mode != 0){
        // 프리미엄 요금제냐는 뜻 - 구독제를 쓰냐는 뜻
        var tier = "p"
        if(tier == "p") {
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

@Composable
fun PostItem(index: Int){

    val isLiked = remember { mutableStateOf(false) } // 좋아요 했는지 여부

    Card(
        modifier = Modifier
            .fillMaxWidth(),
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
                    ProfileInfo(index = index, mode = 0)
                }
                // 기존 좋아요 아이콘
//                IconButton(onClick = { /*TODO*/ }, modifier = Modifier.size(24.dp).bounceClick {  }) {
//                    Icon(
//                        painter = painterResource(
//                            id = R.drawable.ic_heart_solid
//                        ), contentDescription = null,
//                        tint = Color(0xFFFF6767)
//                    )
//                }
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
                        .size(150.dp)
                        .bounceClick { },
                    color = MaterialTheme.colors.onSurface.copy(alpha = 0.2f),
                ) {
                    Image(painterResource(id = R.drawable.ic_home_ex1), contentDescription = "")
                }

                Surface(
                    modifier = Modifier
                        .size(150.dp)
                        .bounceClick { },
                    color = MaterialTheme.colors.onSurface.copy(alpha = 0.2f),
                ) {
                    Image(painterResource(id = R.drawable.ic_home_ex1), contentDescription = "")
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
                        text = "진영이 필터",
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
                        text = "#남캠, #화사",
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
                        Text(text = "3", Modifier
                            .padding(end = 15.dp),fontSize = 13.sp, color = Color.Gray
                        )
                    }
                }
            }
            Spacer(Modifier.width(17.dp))
        }
    }
}