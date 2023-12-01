package com.founder.easy_route_assistant.data.model.response

import com.founder.easy_route_assistant.data.model.request.Place
import com.founder.easy_route_assistant.data.model.request.Type
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
@Serializable

data class ResponseConvenientList(
    @SerialName("convenientDTOList")
    var convenientLists: List<Type>,
    )

