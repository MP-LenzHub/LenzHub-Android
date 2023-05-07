//package com.plzgpt.lenzhub.ui.screen.search
//
//import android.os.Handler
//import android.widget.Toast
//import androidx.compose.foundation.layout.PaddingValues
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.items
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.unit.dp
//import com.plzgpt.lenzhub.api.dto.GetSearchCategoryPost
//import com.plzgpt.lenzhub.ui.data.Category
//import com.plzgpt.lenzhub.util.ShowProgressBar
//import retrofit2.Call
//import retrofit2.Callback
//import retrofit2.Response
//
//
//@Composable
//fun SearchCategoryPayScreen(category: Category){
//    val context = LocalContext.current
//
////    val responseBody  = remember { mutableStateOf(GetSearchPost()) }
//
//    val isLoading = remember { mutableStateOf(true) }
//
////    RetrofitBuilder.postAPI
////        .getCategorySearchPost(category.category)
////        .enqueue(object: Callback<GetSearchPost> {
////            override fun onResponse(
////                call: Call<GetSearchPost>,
////                response: Response<GetSearchPost>
////            ) {
////                if(response.isSuccessful) {
////                    val res = response.body()
////                    if(res != null) {
////                        responseBody.value = res
////                        isLoading.value = false
////                    }
////                }
////                else {
////                    Toast.makeText(context, "데이터를 받지 못했어요", Toast.LENGTH_SHORT).show()
////                }
////            }
////
////            override fun onFailure(call: Call<GetSearchPost>, t: Throwable) {
////                Toast.makeText(context, "서버와 연결하지 못했어요", Toast.LENGTH_SHORT).show()
////            }
////
////        })
//
//    if(isLoading.value){
//        ShowProgressBar()
//        Handler().postDelayed({isLoading.value = false}, 1000L)
//    }
//    else {
////        val posts = responseBody.value.content
//        val posts = listOf(
//            GetSearchCategoryPost(
//                post_id = 1,
//                user_id = 1,
//                title = "산을 더 산 같이 보여주는 필터",
//                author = "rhksrhs33",
//                image = "w.w.w",
//                price = 1000,
//                user_profile_img = "",
//                category_name = "풍경",
//                date = "2023-05-06T1",
//                likes = 10,
//                free = true
//            ),
//            GetSearchCategoryPost(
//                post_id = 2,
//                user_id = 1,
//                title = "코인 떡상하게 만들어주는 필터",
//                author = "kdhhuns2000",
//                image = "w.w.w",
//                price = 200,
//                user_profile_img = "",
//                category_name = "음식",
//                date = "2023-05-06T1",
//                likes = 61,
//                free = false
//            ),
//
//            GetSearchCategoryPost(
//                post_id = 3,
//                user_id = 1,
//                title = "노을을 더 노랗게 만들어주는 필터",
//                author = "seungminister",
//                image = "w.w.w",
//                price = 1000,
//                user_profile_img = "",
//                category_name = "풍경",
//                date = "2023-05-06T1",
//                likes = 6,
//                free = false
//            )
//        )
//
//
//
//        if(posts == null){
//            DiscoverSearchNoResultScreen(category.category)
//            Handler().postDelayed({isLoading.value = false}, 1000L)
//        }
//        else {
//            LazyColumn(
//                modifier = Modifier.fillMaxSize(),
//                contentPadding = PaddingValues(horizontal = 18.dp, vertical = 18.dp)
//            ) { items(
//                items = posts,
//                key = {post -> post.post_id }
//            ) { item ->
//                SearchCategoryCard(item)
//            }
//            }
//        }
//    }
//
//
//
//
//}
//
