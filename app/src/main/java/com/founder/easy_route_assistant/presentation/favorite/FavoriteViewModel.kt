package com.founder.easy_route_assistant.presentation.favorite

import androidx.lifecycle.ViewModel

class FavoriteViewModel : ViewModel() {
    // 가짜 데이터
    val mockFavoriteList = listOf<Favorite>(
        Favorite(
            favoriteName = "동국대학교",
            favoriteAddress = "서울 중구 필동로1길 30(필동 3가)",
        ),
        Favorite(
            favoriteName = "남산타운아파트",
            favoriteAddress = "서울 중구 다산로 32(신당동)",
        ),
        Favorite(
            favoriteName = "서울고속버스터미널(경부)",
            favoriteAddress = "서울 서초구 신반포로 194(반포동)",
        ),
        Favorite(
            favoriteName = "약수역 3호선",
            favoriteAddress = "서울특별시 중구 다산로 지하 122",
        ),
    )
}
