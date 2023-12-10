package com.founder.easy_route_assistant.data.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseRouteDetailDto(
    @SerialName("totalTime")
    val totalTime: String?,
    @SerialName("detailElements")
    val routeDetails: List<RouteDetail>?
) {
    @Serializable
    data class RouteDetail(
        @SerialName("start")
        val departure: String?,
        @SerialName("end")
        val arrival: String?,
        @SerialName("line")
        val subwayNum: String?,
        @SerialName("mode") // WALK, BUS, SUBWAY, elevator
        val moveMode: String,
        @SerialName("name")
        val detail: String?,
        @SerialName("routeColor")
        val routeColor: String?,
        @SerialName("sectionTime")
        val timeSecond: String?,
        @SerialName("arrmsg1")
        val busMessage1: String?,
        @SerialName("arrmsg2")
        val busMessage2: String?,
        @SerialName("distance")
        val distanceMeter: Long?,
        @SerialName("description")
        val description: List<Descriptions>?
    ) {
        @Serializable
        data class Descriptions(
            @SerialName("descriptions")
            val descriptions: List<String>?,
            @SerialName("imgPath")
            val imgPath: String?,
        )
    }
}
