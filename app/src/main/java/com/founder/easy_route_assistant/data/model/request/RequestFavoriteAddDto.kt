package com.founder.easy_route_assistant.data.model.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RequestFavoriteAddDto(
    @SerialName("favoriteId")
    var favoriteId: Int,
    @SerialName("placeName")
    var placeName: String,
    @SerialName("roadNameAddress")
    var roadNameAddress: String,
    @SerialName("point")
    var point: Point
)

