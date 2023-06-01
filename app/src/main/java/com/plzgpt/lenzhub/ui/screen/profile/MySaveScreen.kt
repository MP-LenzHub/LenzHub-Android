package com.plzgpt.lenzhub.ui.screen.profile

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.plzgpt.lenzhub.api.dto.PostListDto
import com.plzgpt.lenzhub.ui.screen.search.DiscoverSearchNoResultScreen
import com.plzgpt.lenzhub.ui.screen.search.SearchCategoryCard
import com.plzgpt.lenzhub.util.ShowProgressBar

@Composable
fun MySaveScreen(posts: PostListDto){
    val context = LocalContext.current


    val isLoading = remember { mutableStateOf(false) }

    if(isLoading.value){
        ShowProgressBar()
//        Handler().postDelayed({isLoading.value = false}, 500L)
    }

            if(posts.postList.isEmpty()){
                DiscoverSearchNoResultScreen("저장 필터")
            }
            else {
                isLoading.value = false
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(horizontal = 18.dp, vertical = 18.dp)
                ) {
                    items(items = posts.postList, key ={it.id}){
                        SearchCategoryCard(it)
                    }
                }
    }
}