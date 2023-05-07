package com.plzgpt.lenzhub.ui.screen.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.plzgpt.lenzhub.R
import com.plzgpt.lenzhub.ui.activity.MainActivity
import com.plzgpt.lenzhub.ui.theme.LHDivider
import com.plzgpt.lenzhub.ui.theme.LHGray
import com.plzgpt.lenzhub.ui.theme.LHLikeIcon
import com.plzgpt.lenzhub.ui.theme.LHMainBackground
import com.plzgpt.lenzhub.util.bounceClick

@Preview
@Composable
fun MainScreen() {
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

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(top = 9.dp)){
        Text(text = "LenzHub", fontSize = 24.sp)

        LazyColumn(state = scrollState,
            contentPadding = PaddingValues(horizontal = 0.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ){
            items(listSize){ index ->
                PostItem(index = index)
            }
        }
    }
}

@Composable
fun PostItem(index: Int){
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

                    Surface(
                        modifier = Modifier.size(24.dp),
                        color = MaterialTheme.colors.onSurface.copy(alpha = 0.2f),
                        shape = CircleShape
                    ) {
                        Image(painterResource(id = R.drawable.ic_animal), contentDescription = "")
                    }
                    Spacer(Modifier.width(8.dp))
                    Text(
                        "#$index eunseob",
                        style = MaterialTheme.typography.subtitle1,
                        fontSize = 17.sp
                    )
                }

                IconButton(onClick = { /*TODO*/ }, modifier = Modifier.size(24.dp).bounceClick {  }) {
                    Icon(
                        painter = painterResource(
                            id = R.drawable.ic_heart_solid
                        ), contentDescription = null,
                        tint = Color(0xFFFF6767)
                    )
                }
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
                        .bounceClick {  },
                    color = MaterialTheme.colors.onSurface.copy(alpha = 0.2f),
                ) {
                    Image(painterResource(id = R.drawable.ic_home_ex1), contentDescription = "")
                }

                Surface(
                    modifier = Modifier
                        .size(150.dp)
                        .bounceClick {  },
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