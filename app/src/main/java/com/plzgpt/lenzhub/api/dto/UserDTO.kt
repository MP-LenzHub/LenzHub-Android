package com.plzgpt.lenzhub.api.dto

import com.google.gson.annotations.SerializedName
import com.plzgpt.lenzhub.ui.screen.lenz.viewmodel.Lenz

data class PostUserRegisterResponse(
    val name: String,
    val userId: String,
    val password: String
)
data class User(
    val userId: String,
    val password: String
)

data class BaseResponse(
    val code: Int,
    val isSuccess: Boolean,
    val message: String,
    val result: Message
)

data class Message(
    val message: String = ""
)


data class GetUserInfoDTO(
    var code: Int,
    var isSuccess: Boolean,
    var message: String,
    var result: UserInfoResDto = UserInfoResDto()
)

data class UserInfoResDto(
    var id: Int = 0,
    var name: String = "",
    var userId: String = "",
    var password: String = "",
    var grade: String = "Basic",
    var profileImg: String? = null,
    var posts: ArrayList<PostItem> = arrayListOf<PostItem>(),
    var likes: ArrayList<Like> = arrayListOf<Like>()
)

data class PostItem(
    var createdDate : String,
    var modifiedDate : String,
    var description : String,
    var id : Int,
    var price : Int,
    var title : String,
    var category : String,
    var beforeFileName : String,
    var afterFileName : String,
    var beforeImg : String,
    var afterImg : String,
    var user : String,
    var likes : ArrayList<Like> = arrayListOf<Like>(),
    var lenz : LenzInfo
)

data class Like(
    var id : Int,
    var user : String,
    var post : Int
)

data class LenzInfo(
    var id : Int,
    var brightness : Float,
    var contrast : Float,
    var backLight : Float,
    var saturate : Float,
    var grain : Float,
    var temperature : Float,
    var sharpen : Float,
    var distortion : Float,
    var post : String
)


// 유저 프로필 정보 받기
data class GetUserProfileInfoResponse(
    var code: Int,
    var isSuccess: Boolean,
    var message: String,
    var result: UserProfileInfo
)
data class UserProfileInfo (

    var userId : String = "",
    var name : String = "",
    var profileImgUrl : String = "",
    var followCounts : Int = 0,
    var likeCounts: Int = 0,
    var likedPosts: PostListDto = PostListDto(),
    var createdPosts: PostListDto = PostListDto(),
)

// 팔로우 하기
data class FollowRequestBody(
    var toUserId: Int,
    var fromUserId: Int
)

data class GetFollowInfoResponseDTO(
    var code: Int,
    var isSuccess: Boolean,
    var message: String,
    var result: FollowListDTO
)

data class FollowListDTO(
    var followList: ArrayList<Follower> = arrayListOf<Follower>(),
)

data class Follower(
    var userName : String,
    var grade : String,
    var filterCount : Int
)
