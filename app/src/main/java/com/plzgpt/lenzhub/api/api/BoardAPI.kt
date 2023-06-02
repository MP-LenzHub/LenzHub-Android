package com.plzgpt.lenzhub.api.api

import com.plzgpt.lenzhub.api.dto.SearchResponseDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface BoardAPI {

    // 게시글 전체 조회
    @GET("/api/board")
    fun postGetAll(
    ): Call<SearchResponseDTO>

}