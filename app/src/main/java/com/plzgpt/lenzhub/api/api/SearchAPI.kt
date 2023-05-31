package com.plzgpt.lenzhub.api.api

import com.plzgpt.lenzhub.api.dto.SearchResponseDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchAPI {

    // 무료 카태고리 검색
    @GET("/api/board/category/free")
    fun getSearchCategoryFreePost(
        @Query("category") category: String
    ): Call<SearchResponseDTO>

    // 유료 카태고리 검색
    @GET("/api/board/category/pay")
    fun getSearchCategoryPayPost(
        @Query("category") category: String
    ): Call<SearchResponseDTO>

    // 전체 검색
    @GET("/api/board")
    fun getAllPost(
    ): Call<SearchResponseDTO>


    @GET("/api/board/title")
    fun searchTextPost(
        @Query("title") title: String
    ): Call<SearchResponseDTO>

}