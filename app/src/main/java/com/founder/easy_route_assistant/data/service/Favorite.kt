package com.founder.easy_route_assistant.data.service

import com.founder.easy_route_assistant.data.model.request.RequestFavoriteAddDto
import com.founder.easy_route_assistant.data.model.response.ResponseFavoriteList
import com.founder.easy_route_assistant.data.model.response.ResponseFavoriteListDto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

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

    @DELETE("favorite/delete/{favoriteId}")
    fun delete(
        @Header("jwt") accessToken: String,
        @Path("favoriteId") type: Int,
    ): Call<ResponseFavoriteListDto>
}
