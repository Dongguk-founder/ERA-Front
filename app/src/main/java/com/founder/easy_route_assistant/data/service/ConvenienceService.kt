package com.founder.easy_route_assistant.data.service

import com.founder.easy_route_assistant.data.model.request.RequestConvenienceApplyDto
import com.founder.easy_route_assistant.data.model.response.ResponseConvenienceDto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ConvenienceService {
    @GET("request/get")
    fun getConvenienceList(
        @Header("jwt") accessToken: String,
    ): Call<ResponseConvenienceDto>

    @POST("request/send")
    fun sendConvenienceApply(
        @Header("jwt") accessToken: String,
        @Body request: RequestConvenienceApplyDto,
    ): Call<Void>
}
