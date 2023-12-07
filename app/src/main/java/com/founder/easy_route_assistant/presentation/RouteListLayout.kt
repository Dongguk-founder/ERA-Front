package com.founder.easy_route_assistant.presentation

import com.founder.easy_route_assistant.data.model.response.RouteElements

class RouteListLayout(
    val id: Long, // 장소명
    val totalTime: String, // 도로명 주소
    val routeElements: List<ElementListLayout>,
)