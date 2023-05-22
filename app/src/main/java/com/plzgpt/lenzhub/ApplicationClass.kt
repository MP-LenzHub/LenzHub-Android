package com.plzgpt.lenzhub

import android.app.Application
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApplicationClass : Application() {
    private val baseUrl = "http://43.201.241.228:8080"

    val gson: Gson = GsonBuilder().setLenient().create()

    companion object {
        lateinit var sharedPreferences: SharedPreferences

        lateinit var retrofit: Retrofit
        var myId = 0
        val clientId = "Client ID"
    }

    override fun onCreate() {
        super.onCreate()
        sharedPreferences =
            applicationContext.getSharedPreferences("LHPref", MODE_PRIVATE)
//         레트로핏 인스턴스 생성
         initRetrofitInstance()
    }

    private fun initRetrofitInstance() {

        retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }
}