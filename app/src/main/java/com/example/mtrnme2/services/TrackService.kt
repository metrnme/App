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

    @HTTP(method = "DELETE", path ="api/v1/user/trk", hasBody = true)
    fun deleteTrack(@Body removeTrack: removeTrack) : Call<GenericResponse>

    @POST("api/v1/user/user_like_trk")
    fun LikeaTrack(@Body likeModel: LikeModel): Call<GenericResponse>


    @POST("api/v1/user/user_unlike_trk")
    fun UnlikeaTrack(@Body likeModel: LikeModel): Call<GenericResponse>

    @POST("api/v1/comment")
    fun postComment(@Body commentModel: CommentModel): Call<GenericResponse>

}


