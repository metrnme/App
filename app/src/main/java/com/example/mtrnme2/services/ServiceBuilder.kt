package com.example.mtrnme2.services

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceBuilder {
    private const val URL="http://10.0.2.2:5000/"

    private val okHttp : OkHttpClient.Builder = OkHttpClient.Builder()

    private val builder = Retrofit.Builder().baseUrl(URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttp.build())

    private val retrofit:Retrofit = builder.build()

    public fun buildservice (): UserService {
        return retrofit.create(UserService::class.java)
    }
    
    
}