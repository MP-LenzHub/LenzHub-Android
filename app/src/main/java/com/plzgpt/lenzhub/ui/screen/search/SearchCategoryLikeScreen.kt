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
import com.plzgpt.lenzhub.api.dto.GetSearchLikeUser
import com.plzgpt.lenzhub.ui.screen.profile.ProfileInfo


@Composable
fun SearchCategoryLikeScreen(id: Int){
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
        Handler().postDelayed({isLoading.value = false}, 500L)
    }
    else {
//        val posts = responseBody.value.content
        val profiles = listOf(
            GetSearchLikeUser(
                user_id = 1,
                user_name = "김땡땡",
                user_profile_img = "https://cdn.pixabay.com/photo/2015/04/23/22/00/tree-736885__480.jpg",
                filter = 10,
                likes = 20,
                free = true
            ),
            GetSearchLikeUser(
                user_id = 2,
                user_name = "오지냉",
                user_profile_img = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQXhS4DYN-f87sg9KxXiDdcP5Ox-qx2KVnM_Q&usqp=CAU",
                filter = 9,
                likes = 29,
                free = false
            ),
            GetSearchLikeUser(
                user_id = 3,
                user_name = "섭다이어터",
                user_profile_img = "https://img.freepik.com/free-photo/cute-puppy-of-maltipoo-dog-posing-calmly-lying-isolated-over-white-studio-background-domestic-animal_155003-45979.jpg",
                filter = 9,
                likes = 11,
                free = true
            ),
            GetSearchLikeUser(
                user_id = 4,
                user_name = "갓승민",
                user_profile_img = "https://cafeptthumb-phinf.pstatic.net/MjAxOTA0MTdfMjk5/MDAxNTU1NDkzNTE1NjUy.cX6tNC_yvoyxy_0ujcWn4DMcrlgUHUjBmL-PJo0nSAAg.QGl1lBKKc9pvdLwTPvhTFhxT62adsyzsxdKft-LGGhcg.JPEG.ht0729/KakaoTalk_20190417_175827332.jpg?type=w800",
                filter = 101,
                likes = 209,
                free = false
            ),

        )



        if(profiles == null){
           // DiscoverSearchNoResultScreen(category.category)
        }
        else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(horizontal = 18.dp, vertical = 18.dp),
                verticalArrangement = Arrangement.spacedBy(18.dp)
            ) { items(
                items = profiles,
                key = {profiles -> profiles.user_id }
            ) { item ->
                ProfileInfo(item)
            }
            }
        }
    }

}

