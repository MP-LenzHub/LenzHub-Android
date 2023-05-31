package com.plzgpt.lenzhub.ui.screen.lenz.viewmodel

import android.util.Log
import com.plzgpt.lenzhub.api.RetrofitBuilder
import com.plzgpt.lenzhub.api.dto.PostCreateReqDto
import com.plzgpt.lenzhub.api.dto.PostCreateRes
import com.plzgpt.lenzhub.api.dto.PostCreateResResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Multipart


class UploadRepository {


    companion object {
        private val sUploadRepository: UploadRepository = UploadRepository()

        fun getInstance(): UploadRepository {
            return sUploadRepository
        }
    }

    suspend fun postUpload(
        userId: Int,
        requestBody: RequestBody,
        beforeFile: MultipartBody.Part,
        afterFile: MultipartBody.Part
    ): PostCreateResResult = withContext(Dispatchers.IO) {
        val response = RetrofitBuilder.lenzAPI.postCreate(
            userId,
            requestBody,
            beforeFile,
            afterFile).execute()

        Log.d("UploadRepository", "postUpload: $userId")
        Log.d("UploadRepository", "postUpload: $requestBody")
        Log.d("UploadRepository", "postUpload: $beforeFile")
        Log.d("UploadRepository", "postUpload: $afterFile")
        Log.d("UploadRepository", "postUpload: $response")
        Log.d("UploadRepository", "postUpload: ${response.body()}")

        response.body()?.result?: PostCreateResResult()
    }
}