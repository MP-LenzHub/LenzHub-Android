package com.plzgpt.lenzhub.api.api

import com.plzgpt.lenzhub.api.dto.CategorySearchResponseDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface SearchAPI {

    @GET("/api/board/{category}")
    fun getSearchCategoryPost(
        @Path("category") category: String
    ): Call<CategorySearchResponseDTO>
}