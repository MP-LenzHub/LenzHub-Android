package com.plzgpt.lenzhub.api

import com.plzgpt.lenzhub.ApplicationClass.Companion.retrofit
import com.plzgpt.lenzhub.api.api.*


object RetrofitBuilder {
    val signupAPI: SignInAPI = retrofit.create(SignInAPI::class.java)

    val boardAPI: BoardAPI = retrofit.create(BoardAPI::class.java)
    val userAPI: UserAPI = retrofit.create(UserAPI::class.java)

    val lenzAPI: LenzAPI = retrofit.create(LenzAPI::class.java)
    val searchAPI: SearchAPI = retrofit.create(SearchAPI::class.java)

}