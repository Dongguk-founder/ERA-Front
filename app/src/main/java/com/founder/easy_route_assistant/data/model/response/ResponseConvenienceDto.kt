package com.founder.easy_route_assistant.data.model.response

import com.founder.easy_route_assistant.presentation.convenience.Convenience
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
        @SerialName("userID")
        val userId: String,
        @SerialName("convenientType")
        val convenienceType: String,
        @SerialName("roadAdd")
        val convenienceAddress: String,
        @SerialName("point")
        val point: Point,
        @SerialName("content")
        val convenienceContent: String,
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
