package com.founder.easy_route_assistant.data.service

import com.founder.easy_route_assistant.data.model.response.ResponseConvenienceDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface ConvenienceService {
    @GET("request/get")
    fun getConvenienceList(
        @Header("jwt") accessToken: String,
    ): Call<ResponseConvenienceDto>
}
