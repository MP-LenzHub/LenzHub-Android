package com.plzgpt.lenzhub.api.api

import com.google.gson.JsonObject
import com.plzgpt.lenzhub.api.dto.LogInResponseDTO
import com.plzgpt.lenzhub.api.dto.SignInResponseDTO
import com.plzgpt.lenzhub.api.dto.User
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface SignInAPI {

    @POST("/api/user/register")
    fun signIn(
        @Body requestBody: RequestBody
    ): Call<SignInResponseDTO>

    @GET("/api/user/login")
    fun login(
        @Query("user") user: User
    ): Call<LogInResponseDTO>
}