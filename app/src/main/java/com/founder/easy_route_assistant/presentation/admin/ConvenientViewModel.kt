package com.founder.easy_route_assistant.presentation.admin

import androidx.lifecycle.ViewModel

class ConvenientViewModel : ViewModel() {
    // 가짜 데이터
    val mockConvenienceList = listOf<Convenient>(
        Convenient(
            convenientType = "엘리베이터",
            convenientAddress = "서울 중구 필동로1길 30(필동 3가)",
            convenientDetail = "3번 출구 지하 2층",
            convenientWeekdays = "평일 07:00 - 20:00",
            convenientSaturday = "토요일 09:00 - 21:00",
            convenientHoliday = "공휴일 08:00 - 17:00",
        ),
        Convenient(
            convenientType = "휠체어 충전소",
            convenientAddress = "서울 강동구 올림픽로 664 대우한강베네시티",
            convenientDetail = "101동 2103호",
            convenientWeekdays = "평일 07:00 - 20:00",
            convenientSaturday = "토요일 09:00 - 21:00",
            convenientHoliday = "공휴일 08:00 - 17:00",
        ),
        Convenient(
            convenientType = "장애인 화장실",
            convenientAddress = "서울 신내로 110 해니시티",
            convenientDetail = "82동 103호",
            convenientWeekdays = "평일 07:00 - 20:00",
            convenientSaturday = "토요일 09:00 - 21:00",
            convenientHoliday = "공휴일 08:00 - 17:00",
        ),
    )
}
