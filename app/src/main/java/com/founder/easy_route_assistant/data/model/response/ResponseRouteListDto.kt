package com.founder.easy_route_assistant.data.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseRouteListDto (
    @SerialName("routeDTOS")
    val routeDTOS: List<RouteDTO>,
)