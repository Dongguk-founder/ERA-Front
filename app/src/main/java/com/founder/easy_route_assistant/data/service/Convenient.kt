package com.founder.easy_route_assistant.data.service

import com.founder.easy_route_assistant.data.model.request.RequestFavoriteAddDto
import com.founder.easy_route_assistant.data.model.response.ResponseConvenientList
import com.founder.easy_route_assistant.data.model.response.ResponseFavoriteList
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface Convenient {
    @GET("favorite/add/{convenientType}")
    fun getConvenientList(
        @Header("jwt") accessToken: String,
        @Path("convenientType") type: String,
    ): Call<ResponseConvenientList>
}