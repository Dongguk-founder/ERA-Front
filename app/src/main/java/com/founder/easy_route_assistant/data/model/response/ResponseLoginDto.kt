package com.founder.easy_route_assistant.data.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
@Serializable
data class ResponseLoginDto(
    @SerialName("jwt")
    val userToken: String,
    @SerialName("role")
    val role: String,
)