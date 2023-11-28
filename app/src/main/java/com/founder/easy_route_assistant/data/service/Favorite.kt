package com.founder.easy_route_assistant.data.service

import com.founder.easy_route_assistant.data.model.request.RequestFavoriteAddDto
import com.founder.easy_route_assistant.data.model.response.ResponseFavoriteAddDto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface Favorite {
    @POST("/favorite/add")
    fun add(
        @Body request: RequestFavoriteAddDto,
    ): Call<ResponseFavoriteAddDto>
}