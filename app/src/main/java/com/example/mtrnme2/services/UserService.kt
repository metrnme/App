package com.example.mtrnme2.services

import com.example.mtrnme2.models.NewUserResponse
import com.example.mtrnme2.models.User
import retrofit2.Call
import retrofit2.http.*

interface UserService {

    @POST("api/v1/n_user")
    fun addUser(@Body newUser: User): Call<NewUserResponse>

    @PUT("api/v1/user")
    fun updateUserInfo(@Body infoUser: User): Call<NewUserResponse>

    @GET("api/v1/user")
    fun getUser(): Call<NewUserResponse>

    @DELETE("api/v1/user")
    fun removeUser(): Call<NewUserResponse>


}