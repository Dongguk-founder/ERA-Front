package com.founder.easy_route_assistant.data.model.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RequestRouteSearchDto(
    @SerialName("start")
    var start: Point,
    @SerialName("end")
    var end: Point,
)