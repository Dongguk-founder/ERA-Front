package com.founder.easy_route_assistant.presentation

import KakaoAPIKeyword
import ListAdapter
import ResultSearchKeyword
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.VISIBLE
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.founder.easy_route_assistant.Utils.MyApplication
import com.founder.easy_route_assistant.Utils.showToast
import com.founder.easy_route_assistant.data.model.request.Point
import com.founder.easy_route_assistant.data.model.request.RequestFavoriteAddDto
import com.founder.easy_route_assistant.data.service.ServicePool.favorite
import com.founder.easy_route_assistant.databinding.ActivityMainBinding
import com.founder.easy_route_assistant.presentation.auth.LoginEmailActivity
import com.founder.easy_route_assistant.presentation.favorite.Favorite
import com.founder.easy_route_assistant.presentation.favorite.FavoriteViewModel
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
        const val API_KEY = "KakaoAK 2dcd28d2ce8633e071c4952b4ddc5061"  // REST API 키
    }

    private lateinit var binding : ActivityMainBinding
    private val listItems = arrayListOf<ListLayout>()   // 리사이클러 뷰 아이템
    private val listAdapter = ListAdapter(listItems)    // 리사이클러 뷰 어댑터
    private var pageNumber = 1      // 검색 페이지 번호
    private var keyword = ""        // 검색 키워드
    private var id=1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        // 리사이클러 뷰
        binding.rvList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rvList.adapter = listAdapter
        // 리스트 아이템 클릭 시 해당 위치로 이동
        listAdapter.setItemClickListener(object: ListAdapter.OnItemClickListener {
            override fun onClick(v: View, position: Int) {
                //검색 목록 리스트 숨기기
                binding.layoutList.visibility=View.GONE
                //상세정보
                binding.tvDetailName.text = listItems[position].name
                binding.tvDetailRoad.text = listItems[position].road
                binding.tvDetailNumber.text = listItems[position].number
                //상세정보 리스트 보이기
                binding.layoutDetailList.visibility= VISIBLE
                //리스트 아이템 마커 출력
                val mapPoint = MapPoint.mapPointWithGeoCoord(listItems[position].y, listItems[position].x)
                binding.mapView.setMapCenterPointAndZoomLevel(mapPoint, 1, true)

                // 즐겨찾기 버튼
                binding.btnFavorite.setOnClickListener {
                    addfavorite(position)
                }

            }
        })

        // 검색 버튼
        binding.btnSearch.setOnClickListener {
            keyword = binding.etSearchField.text.toString()
            pageNumber = 1
            searchKeyword(keyword, pageNumber)

            //상세정보 리스트 숨기고, 검색 목록 리스트 보이기
            binding.layoutDetailList.visibility= View.GONE
            binding.layoutList.visibility= VISIBLE

            //키보드 숨기기
            val manager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            manager.hideSoftInputFromWindow(
                currentFocus!!.windowToken,
                InputMethodManager.HIDE_NOT_ALWAYS
            )

        }

        //리스트 숨기기
        binding.etSearchField.setOnClickListener{
            binding.layoutList.visibility= View.GONE
            binding.layoutDetailList.visibility= View.GONE

        }

        //메뉴바 클릭시
        binding.ivMenu.setOnClickListener{
            binding.layoutMenu.visibility= VISIBLE
            binding.layoutMenu.bringToFront()
        }

        //메뉴바에서 나가기 버튼 클릭시
        binding.ivExit.setOnClickListener{
            binding.layoutMenu.visibility= View.GONE
        }

    }

    //즐겨찾기 추가 함수
    private fun addfavorite(position: Int){
        id++
        val point = Point(listItems[position].x, listItems[position].y)
        val header = MyApplication.prefs.getString("jwt", "")
        favorite.add(header, RequestFavoriteAddDto(id, listItems[position].name, listItems[position].road, point))
            .enqueue(object: Callback<Void>{
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    when (response.code()) {
                        201 -> {
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


    // 키워드 검색 함수
    private fun searchKeyword(keyword: String, page: Int) {
        val retrofit = Retrofit.Builder()          // Retrofit 구성
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val api = retrofit.create(KakaoAPIKeyword::class.java)            // 통신 인터페이스를 객체로 생성
        val call = api.getSearchKeyword(API_KEY, keyword)    // 검색 조건 입력

        // API 서버에 요청
        call.enqueue(object: Callback<ResultSearchKeyword> {
            override fun onResponse(call: Call<ResultSearchKeyword>, response: Response<ResultSearchKeyword>) {
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

    private fun test(){
        showToast("가입")
    }
    // 검색 결과 처리 함수
    private fun addItemsAndMarkers(searchResult: ResultSearchKeyword?) {
        if (!searchResult?.documents.isNullOrEmpty()) {
            // 검색 결과 있음
            listItems.clear()                   // 리스트 초기화
            binding.mapView.removeAllPOIItems() // 지도의 마커 모두 제거
            for (document in searchResult!!.documents) {
                // 결과를 리사이클러 뷰에 추가
                val item = ListLayout(document.place_name,
                    document.road_address_name,
                    document.phone,
                    document.x.toDouble(),
                    document.y.toDouble())
                listItems.add(item)

                // 지도에 마커 추가
                val point = MapPOIItem()
                point.apply {
                    itemName = document.place_name
                    mapPoint = MapPoint.mapPointWithGeoCoord(document.y.toDouble(),
                        document.x.toDouble())
                    markerType = MapPOIItem.MarkerType.BluePin
                    selectedMarkerType = MapPOIItem.MarkerType.RedPin
                }
                binding.mapView.addPOIItem(point)
            }
            listAdapter.notifyDataSetChanged()

        } else {
            // 검색 결과 없음
            listItems.clear()                   // 리스트 초기화
            binding.layoutList.visibility=View.GONE
            binding.mapView.removeAllPOIItems() // 지도의 마커 모두 제거
            listAdapter.notifyDataSetChanged()
            Toast.makeText(this, "검색 결과가 없습니다", Toast.LENGTH_SHORT).show()
        }
    }

}

