package com.plzgpt.lenzhub.api.api

import com.plzgpt.lenzhub.api.dto.*
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface UserAPI {


    // 유저 정보 받기
    @GET("/api/user/{userId}")
    fun getUser(
        @Path("userId") userId: Int
    ): Call<GetUserInfoDTO>

    // 유저 탈퇴
    @DELETE("/api/user/{userId}")
    fun deleteProfile(
        @Path("userId") userId: Int
    ): Call<BaseResponse>

    // 유저 프로필 정보 받기
    @GET("/api/user/profile/{userId}")
    fun getProfileInfo(
        @Path("userId") userId: Int
    ): Call<GetUserProfileInfoResponse>

    // 팔로우 하기
    @POST("/api/follow")
    fun postFollow(
        @Body body: FollowRequestBody
    ): Call<BaseResponse>

    // 팔로우 수정하기
    @PATCH("/api/follow")
    fun patchFollow(
        @Body body: FollowRequestBody
    ): Call<BaseResponse>

    // 팔로우 정보 가져오기
    @GET("/api/follow/{userId}")
    fun getFollowInfo(
        @Path("userId") userId: Int
    ): Call<GetFollowInfoResponseDTO>


}