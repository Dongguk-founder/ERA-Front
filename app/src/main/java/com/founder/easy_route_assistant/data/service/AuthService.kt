package com.founder.easy_route_assistant.data.service

import com.founder.easy_route_assistant.data.model.request.RequestLoginDto
import com.founder.easy_route_assistant.data.model.request.RequestLoginEmailDto
import com.founder.easy_route_assistant.data.model.request.RequestSignUpDto
import com.founder.easy_route_assistant.data.model.response.ResponseLoginDto
import com.founder.easy_route_assistant.data.model.response.ResponseLoginEmailDto
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("/login")
    fun login(
        @Body request: RequestLoginDto,
    ): Call<ResponseLoginDto>

    @POST("/loginEmail")
    fun loginEmail(
        @Body request: RequestLoginEmailDto,
    ): Call<ResponseLoginEmailDto>

    @POST("/join")
    fun signUp(
        @Body request: RequestSignUpDto,
    ): Call<Response<Void>>
}
