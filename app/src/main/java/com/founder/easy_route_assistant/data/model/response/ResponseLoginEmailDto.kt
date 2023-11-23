package com.founder.easy_route_assistant.data.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseLoginEmailDto(
    @SerialName("jwt")
    val token: String,
    @SerialName("role")
    val role: String
)
