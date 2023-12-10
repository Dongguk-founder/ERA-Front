package com.founder.easy_route_assistant.presentation.route

import KakaoAPIKeyword
import ListAdapter
import ResultSearchKeyword
import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.founder.easy_route_assistant.Utils.MyApplication
import com.founder.easy_route_assistant.Utils.showToast
import com.founder.easy_route_assistant.data.model.request.Point
import com.founder.easy_route_assistant.data.model.request.RequestRouteSearchDto
import com.founder.easy_route_assistant.data.model.response.ResponseRouteListDto
import com.founder.easy_route_assistant.data.model.response.RouteDTO
import com.founder.easy_route_assistant.data.service.ServicePool
import com.founder.easy_route_assistant.databinding.ActivityTabBinding
import com.founder.easy_route_assistant.presentation.ListLayout
import com.founder.easy_route_assistant.presentation.MainActivity
import com.founder.easy_route_assistant.presentation.detail_route.RouteDetailActivity
import com.founder.testrecyclerview.RouteDTOAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class RouteTabActivity : AppCompatActivity() {
    companion object {
        const val BASE_URL = "https://dapi.kakao.com/"
        const val API_KEY = "KakaoAK 2dcd28d2ce8633e071c4952b4ddc5061" // REST API 키
    }

    private lateinit var binding: ActivityTabBinding
    private val listItems = arrayListOf<ListLayout>() // 리사이클러 뷰 아이템
    private val listAdapter = ListAdapter(listItems) // 리사이클러 뷰 어댑터
    private var RouteItems = arrayListOf<RouteDTO>()// 리사이클러 뷰 아이템
    private val routeAdapter = RouteDTOAdapter(RouteItems) // 리사이클러 뷰 어댑터
    private var pageNumber = 1 // 검색 페이지 번호
    private var keyword = "" // 검색 키워드
    private var startx = 0.0
    private var starty = 0.0
    private var endx = 0.0
    private var endy = 0.0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTabBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // 리사이클러 뷰
        binding.rvList.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rvList.adapter = listAdapter
        binding.etSearchEnd.hint = intent.getStringExtra("name")
        endx = intent.getDoubleExtra("pointX", 0.0)
        endy = intent.getDoubleExtra("pointY", 0.0)



        routeAdapter.setItemClickListener(object : RouteDTOAdapter.OnItemClickListener {
            override fun onClick(view: View, position: Int) {
                Log.e("POSITION","${position}")
                val nextIntent =
                    Intent(this@RouteTabActivity, RouteDetailActivity::class.java)
                nextIntent.putExtra("id", position)
                startActivity(nextIntent)
            }
        })


        binding.etSearchStart.setOnKeyListener(
            View.OnKeyListener { v, keyCode, event -> // Enter key Action
                if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                    // 키패드 내리기
                    val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(binding.etSearchStart.getWindowToken(), 0)
                    binding.layoutList.visibility = View.VISIBLE
                    binding.layoutList2.visibility = View.GONE

                    // 처리
                    keyword = binding.etSearchStart.text.toString()
                    pageNumber = 1
                    searchKeyword(keyword, pageNumber)
                    listAdapter.setItemClickListener(object : ListAdapter.OnItemClickListener {
                        override fun onClick(v: View, position: Int) {
                            startx = listItems[position].x
                            starty = listItems[position].y
                            binding.layoutList.visibility = View.GONE
                            binding.etSearchStart.setText(listItems[position].name)
                            searchroute()
                            binding.layoutList2.visibility = View.VISIBLE
                        }
                    })
                    return@OnKeyListener true
                }
                false
            },
        )

    }

    private fun searchroute() {
        val startpoint = Point(startx, starty)
        var endpoint = Point(endx, endy)
        val header = MyApplication.prefs.getString("jwt", "")
        ServicePool.route.add(
            header,
            RequestRouteSearchDto(startpoint, endpoint)
        )
            .enqueue(object : Callback<ResponseRouteListDto> {
                override fun onResponse(
                    call: Call<ResponseRouteListDto>,
                    response: Response<ResponseRouteListDto>
                ) {
                    when (response.code()) {
                        200 -> {
                            // 즐겨찾기 추가 성공
                            addItems(response.body())
                            Log.d("Test", "Body: ${response.body()}")
                            showToast("길찾기 리스트 받아오기 성공!")
                        }

                        else -> {
                            showToast("서버 에러 발생")
                        }
                    }
                }

                override fun onFailure(call: Call<ResponseRouteListDto>, t: Throwable) {
                    Log.e(ContentValues.TAG, t.toString())
                    showToast("실패")
                }
            })
    }

    private fun searchKeyword(keyword: String, page: Int) {
        val retrofit = Retrofit.Builder() // Retrofit 구성
            .baseUrl(MainActivity.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val api = retrofit.create(KakaoAPIKeyword::class.java) // 통신 인터페이스를 객체로 생성
        val call = api.getSearchKeyword(
            MainActivity.API_KEY,
            keyword,
            "126.9932215",
            "37.561048",
            20000,
        ) // 검색 조건 입력

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

    private fun addItemsAndMarkers(searchResult: ResultSearchKeyword?) {
        if (!searchResult?.documents.isNullOrEmpty()) {
            // 검색 결과 있음
            listItems.clear() // 리스트 초기화
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
            }
            listAdapter.notifyDataSetChanged()
        } else {
            // 검색 결과 없음
            listItems.clear() // 리스트 초기화
            binding.layoutList.visibility = View.GONE
            listAdapter.notifyDataSetChanged()
            Toast.makeText(this, "검색 결과가 없습니다", Toast.LENGTH_SHORT).show()
        }
    }

    private fun addItems(searchResult: ResponseRouteListDto?) {
        RouteItems.clear()
            for (document in searchResult!!.routeDTOS) {
                // 결과를 리사이클러 뷰에 추가
                val item = RouteDTO(
                    document.id,
                    document.totalTime,
                    document.routeElements
                )
                RouteItems.add(item)
            }

        binding.rvPalette.apply {
            layoutManager =
                LinearLayoutManager(this@RouteTabActivity, LinearLayoutManager.VERTICAL, false)
            adapter = routeAdapter
        }
    }



}
