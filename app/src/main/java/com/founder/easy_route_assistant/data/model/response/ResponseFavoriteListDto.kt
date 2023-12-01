package com.founder.easy_route_assistant.data.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseFavoriteListDto(
    @SerialName("favoriteList")
    val favoriteList: List<FavoriteList>,
) {
    @Serializable
    data class FavoriteList(
        @SerialName("id")
        val id: Long,
        @SerialName("placeName")
        val placeName: String,
        @SerialName("roadNameAddress")
        val placeAddress: String,
        @SerialName("point")
        val point: Point,
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
