package com.founder.easy_route_assistant.data.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseConvenienceDto(
    @SerialName("requestDTOList")
    val convenienceList: List<Convenience>,
) {
    @Serializable
    data class Convenience(
        @SerialName("id")
        val identifier: Int,
        @SerialName("convenientType")
        val convenienceType: String,
        @SerialName("point")
        val point: Point,
        @SerialName("roadAddr")
        val convenienceAddress: String?,
        @SerialName("content")
        val convenienceContent: String,
        @SerialName("accepted")
        val accepted: Boolean,
        @SerialName("userID")
        val userId: String?,
    ) {
        @Serializable
        data class Point(
            @SerialName("x")
            val pointX: Double,
            @SerialName("y")
            val pointY: Double,
        )
    }
}
