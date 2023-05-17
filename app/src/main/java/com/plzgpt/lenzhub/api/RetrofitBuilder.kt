package com.plzgpt.lenzhub.api

import com.plzgpt.lenzhub.ApplicationClass.Companion.retrofit
import com.plzgpt.lenzhub.api.api.SignInAPI


object RetrofitBuilder {
    val signupAPI: SignInAPI = retrofit.create(SignInAPI::class.java)
}