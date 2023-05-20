package com.plzgpt.lenzhub.api.api

import com.plzgpt.lenzhub.api.dto.GetUserInfoResponseDTO
import com.plzgpt.lenzhub.api.dto.SignInResponseDTO
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface UserAPI {

    @GET("/api/user/{userId}")
    fun userProfile(
        @Path("userId") userId: Int
    ): Call<GetUserInfoResponseDTO>
}