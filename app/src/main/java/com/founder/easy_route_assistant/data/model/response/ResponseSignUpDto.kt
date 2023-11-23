package com.founder.easy_route_assistant.data.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
@Serializable
data class ResponseSignUpDto(
    @SerialName("userID")
    val userId: String,
    @SerialName("pwd")
    val userPw: String,
    @SerialName("userName")
    val userName: String,
    @SerialName("userEmail")
    val userEmail: String,
)