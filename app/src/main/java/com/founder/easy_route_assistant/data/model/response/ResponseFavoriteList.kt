package com.founder.easy_route_assistant.data.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseFavoriteList(
    @SerialName("list")
    var list: List<Place>,
){
    @Serializable
    data class Place(
        @SerialName("favoriteId")
        var favoriteId: Int,
        @SerialName("placeName")
        var placeName: String,
        @SerialName("roadNameAddress")
        var roadNameAddress: String,
        @SerialName("point")
        var point: Point,
    ) {
        @Serializable
        data class Point(
            @SerialName("x")
            var x: Double,
            @SerialName("y")
            var y: Double,
        )
    }
}
