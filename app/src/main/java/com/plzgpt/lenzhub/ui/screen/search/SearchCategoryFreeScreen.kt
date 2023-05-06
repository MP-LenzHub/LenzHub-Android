package com.plzgpt.lenzhub.ui.screen.search

import android.os.Handler
import com.plzgpt.lenzhub.ui.data.Category
import com.plzgpt.lenzhub.util.ShowProgressBar


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.plzgpt.lenzhub.api.dto.GetSearchCategoryPost


@Composable
fun SearchCategoryFreeScreen(category: Category){
    val context = LocalContext.current

//    val responseBody  = remember { mutableStateOf(GetSearchPost()) }

    val isLoading = remember { mutableStateOf(true) }

//    RetrofitBuilder.postAPI
//        .getCategorySearchPost(category.category)
//        .enqueue(object: Callback<GetSearchPost> {
//            override fun onResponse(
//                call: Call<GetSearchPost>,
//                response: Response<GetSearchPost>
//            ) {
//                if(response.isSuccessful) {
//                    val res = response.body()
//                    if(res != null) {
//                        responseBody.value = res
//                        isLoading.value = false
//                    }
//                }
//                else {
//                    Toast.makeText(context, "데이터를 받지 못했어요", Toast.LENGTH_SHORT).show()
//                }
//            }
//
//            override fun onFailure(call: Call<GetSearchPost>, t: Throwable) {
//                Toast.makeText(context, "서버와 연결하지 못했어요", Toast.LENGTH_SHORT).show()
//            }
//
//        })

    if(isLoading.value){
        ShowProgressBar()
        Handler().postDelayed({isLoading.value = false}, 1000L)
    }
    else {
//        val posts = responseBody.value.content
        val posts = listOf(
            GetSearchCategoryPost(
                post_id = 1,
                user_id = 1,
                title = "마라탕을 더 맛있게 만들어주는 필터",
                author = "eunsub",
                image = "w.w.w",
                price = 1000,
                user_profile_img = "",
                category_name = "음식",
                date = "2023-05-06T1",
                likes = 0,
                free = true
            ),
            GetSearchCategoryPost(
                post_id = 2,
                user_id = 1,
                title = "마라탕을 싫어하는 사람들을 위한 필터",
                author = "Jinyoung",
                image = "w.w.w",
                price = 200,
                user_profile_img = "",
                category_name = "음식",
                date = "2023-05-06T1",
                likes = 61,
                free = true
            ),

            GetSearchCategoryPost(
                post_id = 3,
                user_id = 1,
                title = "이승민을 이승원으로 바꿔주는 필터",
                author = "seungminister",
                image = "w.w.w",
                price = 1000,
                user_profile_img = "",
                category_name = "인물",
                date = "2023-05-06T1",
                likes = 6,
                free = true
            )
        )



        if(posts == null){
            DiscoverSearchNoResultScreen(category.category)
        }
        else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(horizontal = 18.dp, vertical = 18.dp)
            ) { items(
                items = posts,
                key = {post -> post.post_id }
            ) { item ->
                SearchCategoryCard(item)
            }
            }
        }
    }




}

