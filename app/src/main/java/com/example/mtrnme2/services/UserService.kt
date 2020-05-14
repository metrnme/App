package com.example.mtrnme2.services

import com.example.mtrnme2.models.GenericResponse
import com.example.mtrnme2.models.User
import com.example.mtrnme2.models.UserInfoResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface UserService {

    @POST("api/v1/n_user")
    fun addUser(@Body newUser: User): Call<GenericResponse>

    @PUT("api/v1/user")
    fun updateUserInfo(@Body infoUser: User): Call<GenericResponse>

    @FormUrlEncoded
    @POST("api/v1/user")
    fun getUser(@Field("username") username : String) : Call<UserInfoResponse>

    @POST("api/v1/usertype")
    fun setUserType(@Body userDetails: User): Call<GenericResponse>

    @POST("api/v1/user/inst")
    fun addUserInstruments(@Body userDetails:User): Call<GenericResponse>
}