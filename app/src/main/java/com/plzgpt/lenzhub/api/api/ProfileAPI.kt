package com.plzgpt.lenzhub.api.api

import com.plzgpt.lenzhub.api.dto.LogInResponseDTO
import com.plzgpt.lenzhub.api.dto.SignInResponseDTO
import com.plzgpt.lenzhub.api.dto.User
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ProfileAPI {

    @POST("/api/user/register")
    fun signIn(
        @Body requestBody: RequestBody
    ): Call<SignInResponseDTO>


}