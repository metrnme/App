package com.example.mtrnme2.services

import com.example.mtrnme2.models.AllInstrumentResponse
import com.example.mtrnme2.models.GenericResponse
import com.example.mtrnme2.models.User
import com.example.mtrnme2.models.UserInfoResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface InstrumentService {

    @GET("api/v1/inst")
    fun getInstruments(): Call<AllInstrumentResponse>
}