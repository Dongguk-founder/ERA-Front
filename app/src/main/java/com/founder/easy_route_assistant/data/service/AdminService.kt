package com.founder.easy_route_assistant.data.service

import com.founder.easy_route_assistant.data.model.request.RequestAdminDto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.Path

interface AdminService {
    @PATCH("request/update/{id}/{accepted}")
    fun sendAdminConvenience(
        @Header("jwt") accessToken: String,
        @Body request: RequestAdminDto,
        @Path("id") id: Int,
        @Path("accepted") accepted: Boolean,
    ): Call<Void>
}
