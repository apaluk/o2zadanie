package com.example.o2zadanie.data.ticket.remote

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface O2ActivationApi {

    // zvycajne by som pouzil Moshi a navratova hodnota by bol DTO objekt.. Response<xxxDto>
    @GET("version")
    suspend fun activateCode(@Query("code") code: String): Response<ResponseBody>
}