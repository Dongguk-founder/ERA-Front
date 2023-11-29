package com.founder.easy_route_assistant.data.service

import com.founder.easy_route_assistant.data.model.request.RequestFavoriteAddDto
import com.founder.easy_route_assistant.data.model.response.ResponseFavoriteList
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface Favorite {
    @POST("favorite/add")
    fun add(
        @Header("jwt") accessToken: String,
        @Body request: RequestFavoriteAddDto,
    ): Call<Void>

    @GET("favorite/find")
    fun getFavoriteList(
        @Header("jwt") accessToken: String,
    ): Call<ResponseFavoriteList>
}
