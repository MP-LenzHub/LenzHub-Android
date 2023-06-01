package com.plzgpt.lenzhub.api.dto

import com.plzgpt.lenzhub.ui.screen.lenz.viewmodel.PostAllState
import com.plzgpt.lenzhub.ui.screen.lenz.viewmodel.PostUiState

data class PostCreateReqDto(
    val title: String = "테스트 제목",
    val price: Int = 0,
    val category_name: String = "테스트 카테고리",
    val description: String,
    val lenzBasicInfoDto: LenzBasicInfoDto = LenzBasicInfoDto()
)

data class PostGetRes(
    val code: Int,
    val isSuccess: Boolean,
    val message: String,
    val result: PostUiState
)

data class LenzBasicInfoDto(
    val brightness: Float = 1f,
    val contrast: Float = 1f,
    val backLight: Float = 0f,
    val distortion: Float = 0f,
    val grain: Float = 0f,
    val saturate: Float = 0f,
    val sharpen: Float = 0f,
    val temperature: Float = 0.5f
)

data class PostCreateRes(
    val code: Int = 0,
    val isSuccess: Boolean = false,
    val message: String = "실패",
    val result: PostCreateResResult = PostCreateResResult()
)

data class PostCreateResResult(
    val message: String = "실패"
)

data class PostSaveRes(
    val code: Int,
    val isSuccess: Boolean,
    val message: String,
    val result: PostSaveResResult
)

data class PostSaveResResult(
    val message: String = "실패"
)

data class PostGetSavedRes(
    val code: Int,
    val isSuccess: Boolean,
    val message: String,
    val result: PostGetSavedResResult
)

data class PostGetSavedResResult(
    val postList: ArrayList<PostUiState> = arrayListOf()
)

data class PostGetAllRes(
    val code: Int,
    val isSuccess: Boolean,
    val message: String,
    val result: PostAllState
)

data class PostAllState(
    val postList: List<PostUiState> = listOf()
)