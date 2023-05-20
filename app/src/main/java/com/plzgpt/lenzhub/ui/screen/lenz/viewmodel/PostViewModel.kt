package com.plzgpt.lenzhub.ui.screen.lenz.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class PostUiState(
    val title: String = "렌즈명",
    val description: String = "렌즈 설명",
    val isFree: Boolean = true,
    val originalPhoto: Uri = Uri.EMPTY,
    val modifiedPhoto: Uri = Uri.EMPTY,
    val category: String = "풍경",
    val username: String = "제작자 이름",
    val userPhoto: Uri = Uri.EMPTY
)

class PostViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(PostUiState())
    val uiState: StateFlow<PostUiState> = _uiState.asStateFlow()

    fun getPostUiState(id: Float) {
        // api로 불러오기
    }
}