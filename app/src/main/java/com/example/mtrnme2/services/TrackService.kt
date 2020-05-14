package com.example.mtrnme2.services

import retrofit2.Call
import retrofit2.http.*
import com.example.mtrnme2.models.AllTrackResponse
interface TrackService {

    @GET("api/v1/user/trk")
    fun getAllTracks(): Call<AllTrackResponse>
}
