package com.founder.easy_route_assistant.data.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseRouteDetailDto(
    @SerialName("totalTime")
    val totalTime: String?,
    @SerialName("detailElements")
    val routeDetails: List<RouteDetail>
) {
    @Serializable
    data class RouteDetail(
        @SerialName("start")
        val departure: String?,
        @SerialName("end")
        val arrival: String?,
        @SerialName("mode") // WALK, BUS, SUBWAY, elevator
        val moveMode: String?,
        @SerialName("routeColor")
        val routeColor: String?,
        @SerialName("name")
        val detail: String?,
        @SerialName("line")
        val subwayNum: String?,
        @SerialName("distance")
        val distanceMeter: Long?,
        @SerialName("sectionTime")
        val timeSecond: String?,
        @SerialName("description")
        val description: List<Descriptions>
    ) {
        @Serializable
        data class Descriptions(
            @SerialName("descriptions")
            val descriptions: ArrayList<String>,
            @SerialName("imgPath")
            val imgPath: String,
        )
    }
}
