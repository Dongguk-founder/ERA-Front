package com.founder.easy_route_assistant.data.service

import com.founder.easy_route_assistant.data.model.response.ResponseRouteDetailDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface RouteDetailService {
    @GET("map/get/{id}")
    fun getRouteDetail(
        @Header("jwt") accessToken: String,
        @Path("id") id: Int,
    ): Call<ResponseRouteDetailDto>
}