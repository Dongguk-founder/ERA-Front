package com.founder.easy_route_assistant.data.model.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RequestConvenienceApplyDto(
    @SerialName("convenientType")
    var convenientType: String,
    @SerialName("point")
    var point: Point,
    @SerialName("roadAddr")
    var placeAddr: String,
    @SerialName("content")
    var content: String,
) {
    @Serializable
    data class Point(
        @SerialName("x")
        val pointX: Double,
        @SerialName("y")
        val pointY: Double,
    )
}
