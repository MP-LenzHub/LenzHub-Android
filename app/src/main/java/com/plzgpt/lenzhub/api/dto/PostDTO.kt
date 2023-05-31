package com.plzgpt.lenzhub.api.dto

data class SearchResponseDTO(
    val code: Int ?= null,
    val isSuccess: Boolean ?= null,
    val message: String ?= null,
    val result: PostListDto ?=  null
)
data class PostListDto(
    val postList: ArrayList<GetSearchCategoryPost> = arrayListOf(),
)

data class GetSearchCategoryPost(
    val id: Int,
    val title: String,
    val userName: String,
    val profileImg: String,
    val description: String,
    val price: Int,
    val category_name: String,
    val date: String,
    val beforeFileName: String,
    val afterFileName: String,
    val beforeImg: String,
    val afterImg: String,
    val lenzBasicInfoDto: LenzBasicInfoDto,
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