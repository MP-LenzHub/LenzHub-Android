package com.plzgpt.lenzhub.api.api

import com.plzgpt.lenzhub.api.dto.PostGetAllRes
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface BoardAPI {

    // 게시글 전체 조회
    @GET("/api/board")
    fun postGetAll(
        @Query("page") page: Int,
        @Query("size") size: Int
    ): Call<PostGetAllRes>

}