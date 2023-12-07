package com.founder.easy_route_assistant.data.service

import com.founder.easy_route_assistant.data.model.request.RequestFavoriteAddDto
import com.founder.easy_route_assistant.data.model.request.RequestRouteSearchDto
import com.founder.easy_route_assistant.data.model.response.ResponseRouteListDto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface Route {
    @POST("map/find")
    fun add(
        @Header("jwt") accessToken: String,
        @Body request: RequestRouteSearchDto,
    ): Call<ResponseRouteListDto>
}