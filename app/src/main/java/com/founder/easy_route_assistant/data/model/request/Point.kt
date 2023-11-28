package com.founder.easy_route_assistant.data.model.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Point(
    @SerialName("x")
    var x: Double,
    @SerialName("y")
    var y: Double,
)
