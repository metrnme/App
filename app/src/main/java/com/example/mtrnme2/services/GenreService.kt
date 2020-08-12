package com.example.mtrnme2.services

import com.example.mtrnme2.models.AllGenreResponse
import com.example.mtrnme2.models.AllInstrumentResponse
import retrofit2.Call
import retrofit2.http.GET

interface GenreService {

    @GET("api/v1/genre")
    fun getAllGenre(): Call<AllGenreResponse>
}