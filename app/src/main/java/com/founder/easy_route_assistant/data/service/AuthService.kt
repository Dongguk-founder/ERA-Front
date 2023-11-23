package com.founder.easy_route_assistant.data.service

import com.founder.easy_route_assistant.data.model.request.RequestLoginDto
import com.founder.easy_route_assistant.data.model.response.ResponseLoginDto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("/login")
    fun login(
        @Body request: RequestLoginDto,
    ): Call<ResponseLoginDto>
}