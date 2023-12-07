package com.founder.easy_route_assistant.data.model.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Type(
    @SerialName("convenientType")
    var convenientType: String,
    @SerialName("description")
    var description: String,
    @SerialName("point")
    var point: Point,
    @SerialName("weekday")
    var weekday: String?,
    @SerialName("saturday")
    var saturday: String?,
    @SerialName("holiday")
    var holiday: String?,
)
