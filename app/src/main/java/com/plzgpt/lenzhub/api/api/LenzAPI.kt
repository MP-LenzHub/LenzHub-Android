package com.plzgpt.lenzhub.api.api

import com.plzgpt.lenzhub.api.dto.*
import com.plzgpt.lenzhub.ui.screen.lenz.viewmodel.PostUiState
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface LenzAPI {

    @Multipart
    @POST("/api/board/{userId}")
    fun postCreate(
        @Path("userId") userId: Int,
        @Part("postCreateReqDto") postCreateReqDto: RequestBody,
        @Part beforeImage: MultipartBody.Part?,
        @Part afterImage: MultipartBody.Part?
    ): Call<PostCreateRes>

    @GET("/api/board/{postId}")
    fun postGet(
        @Path("postId") postId: Int
    ): Call<PostGetRes>

    @PUT("/api/board/{userId}/{postId}/likes")
    fun postSave(
        @Path("userId") userId: Int,
        @Path("postId") postId: Int
    ): Call<PostSaveRes>

    @GET("/api/board/{userId}/likes")
    fun postGetSaved(
        @Path("userId") userId: Int
    ): Call<PostGetSavedRes>

}