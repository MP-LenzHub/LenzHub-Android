package com.plzgpt.lenzhub.api.dto

import com.google.gson.annotations.SerializedName

data class PostUserRegisterResponse(
    val name: String,
    val userId: String,
    val password: String
)
data class User(
    val userId: String,
    val password: String
)

data class GetUserInfoResponseDTO(
    val code: Int,
    val isSuccess: Boolean,
    val message: String,
    val result: GetUserInfoResult = GetUserInfoResult()
)

data class GetUserInfoResult (

    var userInfoResDto : ArrayList<UserInfoResDto> = arrayListOf()

)
data class UserInfoResDto (

    var id         : Int?             = null,
    var name       : String?          = null,
    var userId     : String?          = null,
    var password   : String?          = null,
    var grade      : String?          = null,
    var profileImg : String?          = null,
    var posts      : ArrayList<Posts> = arrayListOf(),
    var likes      : ArrayList<Likes> = arrayListOf()
)
data class Posts (

    @SerializedName("createdDate"    ) var createdDate    : String?          = null,
    @SerializedName("modifiedDate"   ) var modifiedDate   : String?          = null,
    @SerializedName("id"             ) var id             : Int?             = null,
    @SerializedName("title"          ) var title          : String?          = null,
    @SerializedName("price"          ) var price          : Int?             = null,
    @SerializedName("category_name"  ) var categoryName   : String?          = null,
    @SerializedName("beforeFileName" ) var beforeFileName : String?          = null,
    @SerializedName("beforeImg"      ) var beforeImg      : String?          = null,
    @SerializedName("afterFileName"  ) var afterFileName  : String?          = null,
    @SerializedName("afterImg"       ) var afterImg       : String?          = null,
    @SerializedName("user"           ) var user           : String?          = null,
    @SerializedName("likes"          ) var likes          : ArrayList<Likes> = arrayListOf()

)
data class Likes (

    @SerializedName("id"   ) var id   : Int?    = null,
    @SerializedName("user" ) var user : String? = null,
    @SerializedName("post" ) var post : String? = null

)
