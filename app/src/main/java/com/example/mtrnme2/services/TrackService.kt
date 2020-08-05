package com.example.mtrnme2.services

import com.example.mtrnme2.models.*
import retrofit2.Call
import retrofit2.http.*

interface TrackService {

    @GET("api/v1/user/trk")
    fun getAllTracks(): Call<AllTrackResponse>

    @POST("api/v1/user/trk")
    fun uploadTrack(@Body uploadTrack: TrackUpload): Call<GenericResponse>

    @POST("api/v1/t_comment")
    fun getTrackComments(@Body trackID: trackID): Call<TrackComments>

    @POST("api/v1/user/user_trk")
    fun getUserTracks(@Body userName: userName) : Call<AllTrackResponse>
}


//mujhe sirf yeh json to kotlin data class wala tareekaa bataa dou, let me try to do it ab.
//lol
//okay?
//sure
