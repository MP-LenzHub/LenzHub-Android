package com.plzgpt.lenzhub.api.dto


data class GetSearchCategoryPost(
    val post_id: Long,
    val user_id: Long,
    val title: String,
    val author: String,
    val image: String,
    val price: Int,
    val user_profile_img: String,
    val category_name: String,
    val date: String,
    val likes: Int,
    val free: Boolean,
)