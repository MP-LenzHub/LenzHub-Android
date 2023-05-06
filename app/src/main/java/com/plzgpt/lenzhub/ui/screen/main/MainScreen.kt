package com.plzgpt.lenzhub.ui.screen.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
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
import com.plzgpt.lenzhub.ui.activity.MainActivity

@Preview
@Composable
fun MainScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
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

    Column(modifier = Modifier.fillMaxSize().padding(top = 9.dp)){
        Text(text = "LenzHub", fontSize = 24.sp)

        LazyColumn(state = scrollState,
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ){
            items(listSize){ index ->
                PostItem(index = index)
            }
        }
    }
}

@Composable
fun PostItem(index: Int){
    Column(){
        Row(verticalAlignment = Alignment.CenterVertically) {
            Surface(
                modifier = Modifier.size(10.dp),
                color = MaterialTheme.colors.onSurface.copy(alpha = 0.2f),
                shape = CircleShape
            ){
                Image(painterResource(id = R.drawable.ic_animal), contentDescription ="")
            }
            Spacer(Modifier.width(10.dp))
            Text("Item #$index", style = MaterialTheme.typography.subtitle1)
            IconButton(onClick = { /*TODO*/ }) {
                Icon(Icons.Filled.Favorite, contentDescription = null)
            }
        }
        Row(verticalAlignment = Alignment.CenterVertically){
            Surface(
                modifier = Modifier.size(50.dp),
                color = MaterialTheme.colors.onSurface.copy(alpha = 0.2f),
            ){
                Image(painterResource(id = R.drawable.ic_animal), contentDescription ="")
            }
            Surface(
                modifier = Modifier.size(50.dp),
                color = MaterialTheme.colors.onSurface.copy(alpha = 0.2f),
            ){
                Image(painterResource(id = R.drawable.ic_animal), contentDescription ="")
            }
        }
        Row(verticalAlignment = Alignment.CenterVertically){
            Text(text="#$index 진영이 필터", modifier = Modifier.padding(start = 10.dp)
                , fontWeight = FontWeight.Bold)
        }
        Row(verticalAlignment = Alignment.CenterVertically){
            Text(text="남캠, 화사", modifier = Modifier.padding(start = 10.dp)
                , fontSize = 14.sp, color = Color.Gray)
            IconButton(onClick = { /*TODO*/ }, Modifier.size(20.dp)) {
                Icon(Icons.Filled.Favorite, contentDescription = null)
            }
        }
    }
}