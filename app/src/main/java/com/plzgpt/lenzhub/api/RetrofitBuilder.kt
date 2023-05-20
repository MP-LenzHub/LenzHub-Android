package com.plzgpt.lenzhub.api

import com.plzgpt.lenzhub.ApplicationClass.Companion.retrofit
import com.plzgpt.lenzhub.api.api.SignInAPI
import com.plzgpt.lenzhub.api.api.UserAPI


object RetrofitBuilder {
    val signupAPI: SignInAPI = retrofit.create(SignInAPI::class.java)

    val userAPI: UserAPI = retrofit.create(UserAPI::class.java)
}