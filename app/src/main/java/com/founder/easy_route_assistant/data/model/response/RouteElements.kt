package com.founder.easy_route_assistant.data.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RouteElements(
    @SerialName("start")
    val start: String,
    @SerialName("end")
    val end: String?,
    @SerialName("mode")
    val mode: String?,
    @SerialName("routeColor")
    val routeColor: String?,
    @SerialName("name")
    val name: String?,
    @SerialName("line")
    val line: String?,
    @SerialName("distance")
    val distance: Long?,
    @SerialName("sectionTime")
    val sectionTime: Long?,
)
