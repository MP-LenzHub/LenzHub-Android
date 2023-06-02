package com.plzgpt.lenzhub.ui.screen.lenz.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plzgpt.lenzhub.api.RetrofitBuilder
import com.plzgpt.lenzhub.api.dto.LenzBasicInfoDto
import com.plzgpt.lenzhub.api.dto.PostSaveResResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

data class PostUiState(
    val id: Int = 0,
    val userIdx : Int = 0,
    val title: String = "렌즈명",
    val userName: String = "제작자 이름",
    val profileImg: String = "",
    val description: String = "렌즈 설명",
    val price: Int = 0,
    val category_name: String = "풍경",
    val date: String = "2021-09-01",
    val beforeImg: String = "",
    val afterImg: String = "",
    val lenzBasicInfoDto: LenzBasicInfoDto = LenzBasicInfoDto(),
    val likedCount: Int = 0,
)


class PostViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(PostUiState())
    val uiState: StateFlow<PostUiState> = _uiState.asStateFlow()

    private val postRepository = PostRepository.getInstance()


    // 포스트 내용 요청
    fun getPostUiState(id: Int) = viewModelScope.launch(Dispatchers.IO) {
        val result = postRepository.getPost(id)
        Log.d("PostViewModel", "getPostUiState: $result")
        Log.d("ProfileId", "getPostUiState: $result")
        _uiState.update {
            it.copy(
                id = result.id,
                userIdx = result.userIdx,
                title = result.title,
                userName = result.userName,
                profileImg = result.profileImg ?: "",
                description = result.description,
                price = result.price,
                category_name = result.category_name,
                date = result.date,
                beforeImg = result.beforeImg,
                afterImg = result.afterImg,
                lenzBasicInfoDto = result.lenzBasicInfoDto,
                likedCount = result.likedCount
            )
        }
    }

    // 포스트 저장
    fun savePost(userId: Int, postId: Int) = viewModelScope.launch(Dispatchers.IO) {
        postRepository.savePost(userId, postId)
    }
}

class PostRepository {
    companion object {
        private var sPostRepository: PostRepository = PostRepository()

        fun getInstance(): PostRepository {
            return sPostRepository
        }
    }

    suspend fun getPost(id: Int): PostUiState = withContext(Dispatchers.IO) {
        val response = RetrofitBuilder.lenzAPI.postGet(id).execute()

        Log.d("PostRepository", "getPost: $id")
        Log.d("PostRepository", "getPost: $response")
        Log.d("PostRepository", "getPost: ${response.body()}")

        response.body()?.result ?: PostUiState()
    }

    suspend fun savePost(userId: Int, postId: Int): PostSaveResResult = withContext(Dispatchers.IO) {
        RetrofitBuilder.lenzAPI.postSave(userId, postId).execute().body()?.result ?: PostSaveResResult()
    }
}