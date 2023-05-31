package com.plzgpt.lenzhub.api.dto

import java.util.logging.Filter

data class CategorySearchResponseDTO(
    val code: Int ?= null,
    val isSuccess: Boolean ?= null,
    val message: String ?= null,
    val result: List<GetSearchCategoryPost> ?= null
)
data class GetSearchCategoryPost(
    val id: Long,
    val title: String,
    val userName: String,
    val profileImg: String,
    val price: Int,
    val category_name: String,
    val date: String,
    val beforeFileName: String,
    val afterFileName: String,
    val beforeImg: String,
    val afterImg: String,
    val likedCount: Int
)


data class GetSearchLikeUser(
    val user_id: Long,
    val user_name: String,
    val user_profile_img: String,
    val filter: Int,
    val likes: Int,
    val free: Boolean
)


//data class GetBoard