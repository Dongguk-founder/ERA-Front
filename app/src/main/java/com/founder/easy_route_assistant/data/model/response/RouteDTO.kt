package com.founder.easy_route_assistant.data.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RouteDTO(
    @SerialName("id")
    val id: Long,
    @SerialName("totalTime")
    val totalTime: String,
    @SerialName("routeElements")
    val routeElements: List<RouteElements>,
)
