package com.founder.easy_route_assistant.data.model.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Point(
    @SerialName("x")
    val x: Double,
    @SerialName("y")
    val y: Double,
)
data class RequestFavoriteAddDto(
    @SerialName("placeName")
    val placeName: String,
    @SerialName("roadNameAddress")
    val roadNameAddress: String,
    @SerialName("point")
    val point: Point
)