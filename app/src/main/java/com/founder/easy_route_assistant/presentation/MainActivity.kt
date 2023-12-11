package com.founder.easy_route_assistant.presentation

import KakaoAPIKeyword
import ListAdapter
import ResultSearchKeyword
import android.content.ContentValues.TAG
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.founder.easy_route_assistant.R
import com.founder.easy_route_assistant.Utils.MyApplication
import com.founder.easy_route_assistant.Utils.showToast
import com.founder.easy_route_assistant.data.model.request.Point
import com.founder.easy_route_assistant.data.model.request.RequestFavoriteAddDto
import com.founder.easy_route_assistant.data.model.response.ResponseConvenientList
import com.founder.easy_route_assistant.data.model.response.ResponseFavoriteList
import com.founder.easy_route_assistant.data.service.ServicePool.convenient
import com.founder.easy_route_assistant.data.service.ServicePool.favorite
import com.founder.easy_route_assistant.databinding.ActivityMainBinding
import com.founder.easy_route_assistant.presentation.convenience.ConvenienceApplyFragment
import com.founder.easy_route_assistant.presentation.convenience.ConvenienceListActivity
import com.founder.easy_route_assistant.presentation.favorite.FavoriteItemFragment
import com.founder.easy_route_assistant.presentation.route.RouteTabActivity
import net.daum.mf.map.api.CalloutBalloonAdapter
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity() {
    companion object {
        const val BASE_URL = "https://dapi.kakao.com/"
        const val API_KEY = "KakaoAK 2dcd28d2ce8633e071c4952b4ddc5061" // REST API 키
    }

    private lateinit var binding: ActivityMainBinding
    private val listItems = arrayListOf<ListLayout>() // 리사이클러 뷰 아이템
    private val listAdapter = ListAdapter(listItems) // 리사이클러 뷰 어댑터
    private var pageNumber = 1 // 검색 페이지 번호
    private var keyword = "" // 검색 키워드

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        // 리사이클러 뷰
        binding.rvList.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rvList.adapter = listAdapter
        binding.mapView.setCalloutBalloonAdapter(CustomBalloonAdapter(layoutInflater)) // 커스텀 말풍선 등록

        // activity_main의 구성
        // 메뉴바, 검색창, 검색 버튼, 검색 결과 리스트, 상세정보 리스트
        // 메뉴바-즐겨찾기 리스트, 메뉴바-편의시설 리스트
        // 태그 버튼, 즐겨찾기 버튼, 편의시설 버튼

        // 메뉴바 클릭시
        binding.ivMenu.setOnClickListener {
            val nextIntent = intent.extras
            binding.layoutMenu.visibility = VISIBLE
            binding.tvUsername.text = nextIntent?.getString("id") + "님"
        }
        // 메뉴바에서 나가기 버튼 클릭시
        binding.ivExit.setOnClickListener {
            binding.layoutMenu.visibility = View.GONE
            binding.btnSearch1.visibility = VISIBLE
        }

        // 메뉴바에서 나가기 버튼 클릭시
        binding.ivDetailExit.setOnClickListener {
            binding.layoutDetailList.visibility = View.GONE
        }

        // 검색 버튼 클릭시->검색 결과 리스트
        binding.btnSearch1.setOnClickListener {
            keyword = binding.etSearchField.text.toString()
            pageNumber = 1
            searchKeyword(keyword, pageNumber)

            // 상세정보 리스트 숨기고, 검색 목록 리스트 보이기
            binding.layoutDetailList.visibility = View.GONE
            binding.layoutList.visibility = VISIBLE

            // 키보드 숨기기
            val manager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            manager.hideSoftInputFromWindow(
                currentFocus!!.windowToken,
                InputMethodManager.HIDE_NOT_ALWAYS,
            )
        }

        // 리스트 아이템 클릭시->상세 정보 리스트
        listAdapter.setItemClickListener(object : ListAdapter.OnItemClickListener {
            override fun onClick(v: View, position: Int) {
                // 검색 목록 리스트 숨기기
                binding.layoutList.visibility = View.GONE
                // 상세정보
                binding.tvDetailName.text = listItems[position].name
                binding.tvDetailRoad.text = listItems[position].road
                binding.tvDetailNumber.text = listItems[position].number
                // 상세정보 리스트 보이기
                binding.layoutDetailList.visibility = VISIBLE
                // 리스트 아이템 마커 출력
                val mapPoint =
                    MapPoint.mapPointWithGeoCoord(listItems[position].y, listItems[position].x)
                binding.mapView.setMapCenterPointAndZoomLevel(mapPoint, 1, true)

                // 즐겨찾기 버튼
                binding.btnFavorite.setOnClickListener {
                    addfavorite(position)
                }
                // 편의시설 추가 버튼
                binding.btnConvenient.setOnClickListener {
                    val bundle = Bundle()
                    bundle.putDouble("pointX", listItems[position].x)
                    bundle.putDouble("pointY", listItems[position].y)
                    bundle.putString("name", listItems[position].name)
                    bundle.putString("address", listItems[position].road)

                    val convenienceApplyFragment = ConvenienceApplyFragment()
                    convenienceApplyFragment.arguments = bundle

                    convenienceApplyFragment.show(
                        supportFragmentManager,
                        ConvenienceApplyFragment.TAG,
                    )
                }

                binding.btnRoutesearch.setOnClickListener {
                    val nextIntent =
                        Intent(this@MainActivity, RouteTabActivity::class.java)
                    nextIntent.putExtra("pointX", listItems[position].x)
                    nextIntent.putExtra("pointY", listItems[position].y)
                    nextIntent.putExtra("name", listItems[position].name)
                    startActivity(nextIntent)
                }
            }
        })

        // 메뉴바 - 즐겨찾기 리스트 텍스트뷰 클릭시
        binding.tvFavoriteList.setOnClickListener {
            binding.btnSearch1.visibility = View.INVISIBLE
            val fragmentManager = supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            val fragment = FavoriteItemFragment()
            fragmentTransaction.replace(R.id.layout_constraint, fragment)
            fragmentTransaction.commit()
        }

        // 메뉴바 - 편의시설 리스트 클릭 시
        binding.tvRequestList.setOnClickListener {
            val intent = Intent(this, ConvenienceListActivity::class.java)
            startActivity(intent)
        }

        // 즐겨찾기 태그 클릭시
        binding.btnFavoriteMarker.setOnClickListener {
            showFavoriteList()
            changeColor()
            binding.btnFavoriteMarker.setBackgroundColor(Color.parseColor("#AECD8C"))
        }

        // 엘리베이터 태그 클릭시
        binding.btnElevator.setOnClickListener {
            changeColor()
            binding.btnElevator.setBackgroundColor(Color.parseColor("#AECD8C"))
            showConvenientList("elevator")
        }

        // 엘리베이터 태그 클릭시
        binding.btnCharger.setOnClickListener {
            changeColor()
            binding.btnCharger.setBackgroundColor(Color.parseColor("#AECD8C"))
            showConvenientList("charger")
        }

        // 엘리베이터 태그 클릭시
        binding.btnBathroom.setOnClickListener {
            changeColor()
            binding.btnBathroom.setBackgroundColor(Color.parseColor("#AECD8C"))
            showConvenientList("bathroom")
        }

        binding.etSearchField.setOnClickListener { binding.layoutList.visibility = View.GONE }
    }

    // 즐겨찾기 신청 버튼 클릭시 서버통신 함수
    private fun addfavorite(position: Int) {
        val point = Point(listItems[position].x, listItems[position].y)
        val header = MyApplication.prefs.getString("jwt", "")
        favorite.add(
            header,
            RequestFavoriteAddDto(listItems[position].name, listItems[position].road, point),
        )
            .enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    when (response.code()) {
                        200 -> {
                            // 즐겨찾기 추가 성공
                            showToast("즐겨찾기 추가 성공!")
                        }

                        400 -> {
                            // 중복
                            showToast("이미 즐겨찾기에 추가되어있습니다.")
                        }

                        else -> {
                            showToast("서버 에러 발생")
                        }
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Log.e(TAG, t.toString())
                    showToast("실패")
                }
            })
    }

    // 즐겨찾기 태그 버튼 클릭시 서버 통신 함수
    private fun showFavoriteList() {
        val header = MyApplication.prefs.getString("jwt", "")
        favorite.getFavoriteList(header).enqueue(object : Callback<ResponseFavoriteList> {
            override fun onResponse(
                call: Call<ResponseFavoriteList>,
                response: Response<ResponseFavoriteList>,
            ) {
                when (response.code()) {
                    200 -> {
                        // 즐겨찾기 리스트 get 성공
                        showToast("즐겨찾기 get 성공!")
                        addfavoritemarker(response.body())

                        // 통신 성공
                        Log.d("Test", "Body: ${response.body()}")
                    }

                    else -> {
                        showToast("서버 에러 발생")

                        Log.d("Test", "Body: ${response.body()}")
                        addfavoritemarker(response.body())
                    }
                }
            }

            override fun onFailure(call: Call<ResponseFavoriteList>, t: Throwable) {
                // 통신 실패
                Log.w("LocalSearch", "통신 실패: ${t.message}")
            }
        })
    }

    // 편의시설 태그 클릭시 서버 통신 함수
    private fun showConvenientList(keyword: String) {
        val header = MyApplication.prefs.getString("jwt", "")
        convenient.getConvenientList(header, keyword)
            .enqueue(object : Callback<ResponseConvenientList?> {
                override fun onResponse(
                    call: Call<ResponseConvenientList?>,
                    response: Response<ResponseConvenientList?>,
                ) {
                    when (response.code()) {
                        202 -> {
                            // 즐겨찾기 리스트 get 성공
                            showToast("${keyword} get 성공!")
                            addconvenientmarker(response.body())

                        }

                        else -> {
                            showToast("서버 에러 발생")

                            addconvenientmarker(response.body())
                        }
                    }
                }

                override fun onFailure(call: Call<ResponseConvenientList?>, t: Throwable) {
                    // 통신 실패
                    Log.w("LocalSearch", "통신 실패: ${t.message}")
                }
            })
    }

    // 검색 버튼 클릭시 서버 통신 함수
    private fun searchKeyword(keyword: String, page: Int) {
        val retrofit = Retrofit.Builder() // Retrofit 구성
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val api = retrofit.create(KakaoAPIKeyword::class.java) // 통신 인터페이스를 객체로 생성
        val call =
            api.getSearchKeyword(API_KEY, keyword, "126.9932215", "37.561048", 20000) // 검색 조건 입력

        // API 서버에 요청
        call.enqueue(object : Callback<ResultSearchKeyword> {
            override fun onResponse(
                call: Call<ResultSearchKeyword>,
                response: Response<ResultSearchKeyword>,
            ) {
                // 통신 성공
                addItemsAndMarkers(response.body())
                Log.d("Test", "Body: ${response.body()}")
            }

            override fun onFailure(call: Call<ResultSearchKeyword>, t: Throwable) {
                // 통신 실패
                Log.w("LocalSearch", "통신 실패: ${t.message}")
            }
        })
    }

    // 즐겨찾기 목록 마커 출력
    private fun addfavoritemarker(favoriteList: ResponseFavoriteList?) {
        if (!favoriteList?.favoriteLists.isNullOrEmpty()) {
            binding.mapView.removeAllPOIItems() // 지도의 마커 모두 제거
            for (list in favoriteList!!.favoriteLists) {
                // 지도에 마커 추가
                val points = MapPOIItem()
                points.apply {
                    itemName = list.placeName
                    mapPoint = MapPoint.mapPointWithGeoCoord(
                        list.point.y,
                        list.point.x,
                    )
                    markerType = MapPOIItem.MarkerType.CustomImage
                    customImageResourceId = R.drawable.tag_marker
                    isCustomImageAutoscale = true
                    selectedMarkerType = MapPOIItem.MarkerType.CustomImage
                    customSelectedImageResourceId = R.drawable.tag_marker
                    userObject = arrayListOf(list.roadNameAddress, "정보 없음", "정보 없음", "정보 없음")
                }
                binding.mapView.addPOIItem(points)
                binding.mapView.setMapCenterPointAndZoomLevel(points.mapPoint, 1, true)
            }
        }
    }

    // 편의시설 마커 출력
    private fun addconvenientmarker(convenientList: ResponseConvenientList?) {
        if (!convenientList?.convenientLists.isNullOrEmpty()) {
            binding.mapView.removeAllPOIItems() // 지도의 마커 모두 제거
            for (list in convenientList!!.convenientLists) {
                // 지도에 마커 추가
                val points = MapPOIItem()
                points.apply {
                    itemName = list.convenientType
                    mapPoint = MapPoint.mapPointWithGeoCoord(
                        list.point.y,
                        list.point.x,
                    )
                    markerType = MapPOIItem.MarkerType.CustomImage
                    customImageResourceId = R.drawable.tag_marker
                    isCustomImageAutoscale = true
                    selectedMarkerType = MapPOIItem.MarkerType.CustomImage
                    customSelectedImageResourceId = R.drawable.tag_marker
                    if (list.weekday == null) {
                        list.weekday = "정보 없음"
                    }
                    if (list.holiday == null) {
                        list.holiday = "정보 없음"
                    }
                    if (list.saturday == null) {
                        list.saturday = "정보 없음"
                    }
                    userObject =
                        arrayListOf(list.description, list.weekday, list.holiday, list.saturday)
                }
                binding.mapView.addPOIItem(points)
                binding.mapView.setMapCenterPointAndZoomLevel(points.mapPoint, 1, true)
            }
        }
    }

    // 검색 결과 처리 함수 + 마커 출력
    private fun addItemsAndMarkers(searchResult: ResultSearchKeyword?) {
        if (!searchResult?.documents.isNullOrEmpty()) {
            // 검색 결과 있음
            listItems.clear() // 리스트 초기화
            binding.mapView.removeAllPOIItems() // 지도의 마커 모두 제거
            for (document in searchResult!!.documents) {
                // 결과를 리사이클러 뷰에 추가
                val item = ListLayout(
                    document.place_name,
                    document.road_address_name,
                    document.phone,
                    document.x.toDouble(),
                    document.y.toDouble(),
                )
                listItems.add(item)

                // 지도에 마커 추가
                val point = MapPOIItem()
                point.apply {
                    itemName = document.place_name
                    mapPoint = MapPoint.mapPointWithGeoCoord(
                        document.y.toDouble(),
                        document.x.toDouble(),
                    )
                    markerType = MapPOIItem.MarkerType.CustomImage
                    customImageResourceId = R.drawable.tag_marker
                    isCustomImageAutoscale = true
                    selectedMarkerType = MapPOIItem.MarkerType.CustomImage
                    customSelectedImageResourceId = R.drawable.tag_marker
                    userObject = arrayListOf(document.address_name, "정보 없음", "정보 없음", "정보 없음")
                }
                binding.mapView.addPOIItem(point)
            }
            listAdapter.notifyDataSetChanged()
        } else {
            // 검색 결과 없음
            listItems.clear() // 리스트 초기화
            binding.layoutList.visibility = View.GONE
            binding.mapView.removeAllPOIItems() // 지도의 마커 모두 제거
            listAdapter.notifyDataSetChanged()
            Toast.makeText(this, "검색 결과가 없습니다", Toast.LENGTH_SHORT).show()
        }
    }

    private fun changeColor() {
        binding.btnFavoriteMarker.setBackgroundColor(Color.parseColor("#d4d2d2"))
        binding.btnElevator.setBackgroundColor(Color.parseColor("#d4d2d2"))
        binding.btnBathroom.setBackgroundColor(Color.parseColor("#d4d2d2"))
        binding.btnCharger.setBackgroundColor(Color.parseColor("#d4d2d2"))
    }

    // 커스텀 말풍선 클래스
    class CustomBalloonAdapter(inflater: LayoutInflater) : CalloutBalloonAdapter {
        val mCalloutBalloon: View = inflater.inflate(R.layout.balloon_layout, null)
        val name: TextView = mCalloutBalloon.findViewById(R.id.ball_tv_name)
        val address: TextView = mCalloutBalloon.findViewById(R.id.ball_tv_address)

        override fun getCalloutBalloon(poiItem: MapPOIItem?): View {
            // 마커 클릭 시 나오는 말풍선
            name.text = poiItem?.itemName // 해당 마커의 정보 이용 가능
            val result = poiItem?.userObject.toString().split("[", ",", "]")
            address.text = result[1]
            return mCalloutBalloon
        }

        override fun getPressedCalloutBalloon(poiItem: MapPOIItem?): View {
            val result = poiItem?.userObject.toString().split("[", ",", "]")
            // 말풍선 클릭 시
            address.text =
                "weekday: " + result[2] + "\n" + "holiday: " + result[3] + "\n" + "saturday: " + result[4]
            return mCalloutBalloon
        }
    }
}
