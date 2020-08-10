package com.example.mtrnme2.services
import com.example.mtrnme2.models.*
import retrofit2.Call
import retrofit2.http.*

interface PlaylistService {
    @HTTP(method = "DELETE", path ="api/v1/play_t", hasBody = true)
    fun deleteTrackPlaylist(@Body removeTrack: removeTrack):Call<GenericResponse>

    @POST("api/v1/play_p")
    fun getPlaylist( @Body userName:userName): Call<AllPlaylistResponse>

    @POST("api/v1/play")
    fun postPlaylist(@Body createPlaylist: CreatePlaylist): Call<GenericResponse>

    @PUT("api/v1/play")
    fun updatePlaylist(@Body updatePlaylist: updatePlaylist): Call<GenericResponse>

    @HTTP(method = "DELETE", path ="api/v1/play", hasBody = true)
    fun deletePlaylist(@Body deletePlaylist: playlistID):Call<GenericResponse>

    @POST("api/v1/play_t")
    fun getPlaylistTracks(@Body getPlaylistTracks: ArrayList<Int>): Call<AllTrackResponse>

}

