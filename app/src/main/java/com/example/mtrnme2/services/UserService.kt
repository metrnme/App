package com.example.mtrnme2.services

import com.example.mtrnme2.models.*
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface UserService {

    @POST("api/v1/n_user")
    fun addUser(@Body newUser: User): Call<GenericResponse>

    @PUT("api/v1/user")
    fun updateUserInfo(@Body infoUser: User): Call<GenericResponse>

    @POST("api/v1/user")
    fun getUser(@Body User: userName) : Call<AllUserResponseItem>

    @POST("api/v1/usertype")
    fun setUserType(@Body userDetails: UserType): Call<GenericResponse>

    @POST("api/v1/user/inst")
    fun addUserInstruments(@Body userDetails:User): Call<GenericResponse>

    @GET("api/v1/users")
    fun getAllUsers(): Call<AllUserResponse>

    @PUT("api/v1/follow")
    fun userFollow(@Body followUser: followUser): Call<GenericResponse>

    @PUT("api/v1/unfollow")
    fun userUnfollow(@Body followUser: followUser): Call<GenericResponse>

}