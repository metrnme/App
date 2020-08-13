package com.example.mtrnme2.services

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object ServiceBuilder {
    private const val URL="http://3.22.118.101:5000/"
//        "http://192.168.10.10:5000/"

    private val okHttp : OkHttpClient.Builder = OkHttpClient.Builder()
    private val builder = Retrofit.Builder().baseUrl(URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttp.build())

    private val retrofit:Retrofit = builder.build()

    public fun buildservice (): UserService {
        return retrofit.create(UserService::class.java)
    }
    public fun buildInstrumentService(): InstrumentService {
        return retrofit.create(InstrumentService::class.java)
    }
    public fun buildTrackService(): TrackService {
        return retrofit.create(TrackService::class.java)
    }
    public fun buildPlaylistService(): PlaylistService {
        return retrofit.create(PlaylistService::class.java)
    }

    public fun buildGenreService(): GenreService {
        return retrofit.create(GenreService::class.java)
    }


    
}