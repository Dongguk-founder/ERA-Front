package com.founder.easy_route_assistant.data.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseRouteDetailDto (
    @SerialName("routeDetails")
    val routeDetails: List<RouteDetail>
){
    @Serializable
    data class RouteDetail(
        @SerialName("start")
        val departure: String,
        @SerialName("end")
        val arrival: String,
        @SerialName("line")
        val subwayNum: String,
        @SerialName("mode") // WALK, BUS, SUBWAY, elevator
        val moveMode: String,
        @SerialName("name")
        val detail: String,
        @SerialName("routeColor")
        val routeColor: String,
        @SerialName("sectionTime")
        val timeSecond: Long,
        @SerialName("distance")
        val distanceMeter: Long,
        @SerialName("details")
        val description: List<Descriptions>
    ) {
        @Serializable
        data class Descriptions(
            @SerialName("id")
            val descriptionNum: Long,
            @SerialName("description")
            val descriptions: List<String>,
            @SerialName("imgPath")
            val imgPath: String,
        )
    }
}
