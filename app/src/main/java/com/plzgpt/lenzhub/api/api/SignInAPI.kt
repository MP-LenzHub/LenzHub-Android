package com.plzgpt.lenzhub.api.api

import com.google.gson.JsonObject
import com.plzgpt.lenzhub.api.dto.SignInResponseDTO
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface SignInAPI {

    @POST("/api/user/register")
    fun signIn(
        @Body requestBody: RequestBody
    ): Call<SignInResponseDTO>
}