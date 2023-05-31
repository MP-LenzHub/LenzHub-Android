package com.plzgpt.lenzhub.ui.screen.search

import android.widget.Toast
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
import com.plzgpt.lenzhub.api.RetrofitBuilder
import com.plzgpt.lenzhub.api.dto.SearchResponseDTO
import com.plzgpt.lenzhub.ui.data.Category
import com.plzgpt.lenzhub.util.ShowProgressBar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


@Composable
fun SearchCategoryPayScreen(category: Category){
    val context = LocalContext.current

    val responseBody  = remember { mutableStateOf(SearchResponseDTO()) }

    val isLoading = remember { mutableStateOf(true) }
    RetrofitBuilder.searchAPI
        .getSearchCategoryPayPost(category.category)
        .enqueue(object: Callback<SearchResponseDTO> {
            override fun onResponse(
                call: Call<SearchResponseDTO>,
                response: Response<SearchResponseDTO>
            ) {
                val res = response.body()
                if(res != null){
                    if(res.isSuccess == true) {
                        responseBody.value = res
                        isLoading.value = false

                    }else {
                        Toast.makeText(context, response.message(), Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onFailure(call: Call<SearchResponseDTO>, t: Throwable) {
                Toast.makeText(context, "서버와 연결하지 못했어요", Toast.LENGTH_SHORT).show()
            }

        })

    if(isLoading.value){
        ShowProgressBar()
    }
    else {
        val posts = responseBody.value.result?.postList

        if (posts != null) {
            if(posts.isEmpty()){
                DiscoverSearchNoResultScreen(category.category)
    //            Handler().postDelayed({isLoading.value = false}, 1000L)
            }
            else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(horizontal = 18.dp, vertical = 18.dp)
                ) { items(
                    items = posts,
                    key = {post -> post.id }
                ) { item ->
                    SearchCategoryCard(item)
                }
                }
            }
        }
    }




}
