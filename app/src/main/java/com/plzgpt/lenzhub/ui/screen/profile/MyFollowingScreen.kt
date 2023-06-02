package com.plzgpt.lenzhub.ui.screen.search

import android.content.Intent
import android.os.Handler
import android.util.Log
import com.plzgpt.lenzhub.util.ShowProgressBar


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.plzgpt.lenzhub.api.dto.FollowListDTO
import com.plzgpt.lenzhub.api.dto.GetSearchLikeUser
import com.plzgpt.lenzhub.ui.screen.profile.ProfileActivity
import com.plzgpt.lenzhub.ui.screen.profile.ProfileInfo
import com.plzgpt.lenzhub.util.bounceClick


@Composable
fun SearchCategoryLikeScreen(followList: FollowListDTO){
    val context = LocalContext.current

    val isLoading = remember { mutableStateOf(false) }

    if(isLoading.value){
        ShowProgressBar()
        Handler().postDelayed({isLoading.value = false}, 500L)
    }
    else {
//        val profiles = listOf(
//            GetSearchLikeUser(
//                user_id = 1,
//                user_name = "김땡땡",
//                user_profile_img = "https://cdn.pixabay.com/photo/2015/04/23/22/00/tree-736885__480.jpg",
//                filter = 10,
//                likes = 20,
//                free = true
//            ),
//            GetSearchLikeUser(
//                user_id = 2,
//                user_name = "오지냉",
//                user_profile_img = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQXhS4DYN-f87sg9KxXiDdcP5Ox-qx2KVnM_Q&usqp=CAU",
//                filter = 9,
//                likes = 29,
//                free = false
//            ),
//            GetSearchLikeUser(
//                user_id = 3,
//                user_name = "섭다이어터",
//                user_profile_img = "https://img.freepik.com/free-photo/cute-puppy-of-maltipoo-dog-posing-calmly-lying-isolated-over-white-studio-background-domestic-animal_155003-45979.jpg",
//                filter = 9,
//                likes = 11,
//                free = true
//            ),
//            GetSearchLikeUser(
//                user_id = 4,
//                user_name = "갓승민",
//                user_profile_img = "https://cafeptthumb-phinf.pstatic.net/MjAxOTA0MTdfMjk5/MDAxNTU1NDkzNTE1NjUy.cX6tNC_yvoyxy_0ujcWn4DMcrlgUHUjBmL-PJo0nSAAg.QGl1lBKKc9pvdLwTPvhTFhxT62adsyzsxdKft-LGGhcg.JPEG.ht0729/KakaoTalk_20190417_175827332.jpg?type=w800",
//                filter = 101,
//                likes = 209,
//                free = false
//            ),
//
//        )



        if(followList.followList.size == 0){
           DiscoverSearchNoResultScreen("팔로잉 유저")
        }
        else {
            isLoading.value = false
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(horizontal = 18.dp, vertical = 18.dp),
                verticalArrangement = Arrangement.spacedBy(18.dp)
            ) { items(
                items = followList.followList,
            ) { item ->
                    ProfileInfo(item)
            }
            }
        }
    }

}

