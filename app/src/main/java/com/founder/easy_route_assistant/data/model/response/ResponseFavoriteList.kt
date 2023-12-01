package com.founder.easy_route_assistant.data.model.response

import com.founder.easy_route_assistant.data.model.request.Place
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseFavoriteList(
    @SerialName("favoriteList")
    var favoriteLists: List<Place>,
)
