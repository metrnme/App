package com.example.mtrnme2.services

import com.example.mtrnme2.models.*
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface InstrumentService {

    @GET("api/v1/inst")
    fun getInstruments(): Call<AllInstrumentResponse>

    @PUT("api/v1/user/inst")
    fun addInstrument(@Body selectedInstruments: AddUserInstruments): Call<GenericResponse>
}