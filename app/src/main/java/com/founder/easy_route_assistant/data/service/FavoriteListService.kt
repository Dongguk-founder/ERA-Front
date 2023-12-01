package com.founder.easy_route_assistant.data.service

import com.founder.easy_route_assistant.data.model.response.ResponseFavoriteListDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface FavoriteListService {
    @GET("favorite/find")
    fun getFavoriteList(
        @Header("jwt") accessToken: String,
    ): Call<ResponseFavoriteListDto>
}
