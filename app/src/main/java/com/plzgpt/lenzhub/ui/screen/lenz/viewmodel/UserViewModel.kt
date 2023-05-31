package com.plzgpt.lenzhub.ui.screen.lenz.viewmodel

import android.net.Uri
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class UserViewModel {

    private val _uiState = MutableStateFlow(UploadUiState(photo = Uri.EMPTY))
    val uiState: StateFlow<UploadUiState> = _uiState.asStateFlow()

    private val uploadRepository = UploadRepository.getInstance()

    // 포스트 전체
//    fun getAllPostState(page: Int, size: Int) = viewModelScope.launch(Dispatchers.IO) {
//        val result = postRepository.getAllPost(page, size)
//        _allPostState.update {
//            it.copy(
//                postList = result.postList
//            )
//        }
//    }
}