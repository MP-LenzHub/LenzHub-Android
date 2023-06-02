package com.plzgpt.lenzhub.ui.screen.lenz.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plzgpt.lenzhub.api.RetrofitBuilder
import com.plzgpt.lenzhub.api.dto.PostListDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel : ViewModel() {

    private val _allPostState = MutableStateFlow(PostListDto())
    val allPostState: StateFlow<PostListDto> = _allPostState.asStateFlow()

    private val boardRepository = BoardRepository.getInstance()

    // 포스트 전체
    fun getAllPostState(page: Int, size: Int) = viewModelScope.launch(Dispatchers.IO) {
        val result = boardRepository.getAllPost(page, size)
        _allPostState.update {
            it.copy(
                postList = result.postList
            )
        }
    Log.d("HomeViewModel", "getAllPostState: ${result.postList}")
    }

}

class BoardRepository {
    companion object {
        private var sBoardRepository: BoardRepository = BoardRepository()

        fun getInstance(): BoardRepository {
            return sBoardRepository
        }
    }

    suspend fun getAllPost(page: Int, size:Int): PostListDto = withContext(Dispatchers.IO) {
        RetrofitBuilder.boardAPI.postGetAll().execute().body()?.result?: PostListDto()
    }
}