package com.example.mtrnme2.services

import com.example.mtrnme2.models.NewUserResponse
import com.example.mtrnme2.models.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.PUT

interface UserService {

    @POST("api/v1/n_user")
    fun addUser(@Body newUser: User): Call<NewUserResponse>

    @PUT("api/v1/user")
    fun updateUserInfo(@Body infoUser: User): Call<NewUserResponse>

}