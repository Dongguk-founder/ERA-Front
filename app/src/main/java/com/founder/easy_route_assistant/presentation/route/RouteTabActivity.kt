package com.founder.easy_route_assistant.presentation.route

import KakaoAPIKeyword
import ListAdapter
import ResultSearchKeyword
import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.founder.easy_route_assistant.R
import com.founder.easy_route_assistant.databinding.ActivityMainBinding
import com.founder.easy_route_assistant.databinding.ActivityTabBinding
import com.founder.easy_route_assistant.presentation.ListLayout
import com.founder.easy_route_assistant.presentation.MainActivity
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
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
    private var pageNumber = 1 // 검색 페이지 번호
    private var keyword = "" // 검색 키워드

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTabBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // 리사이클러 뷰
        binding.rvList.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rvList.adapter = listAdapter

        binding.etSearchEnd.hint = "도착지임"

        binding.etSearchStart.setOnKeyListener(View.OnKeyListener { v, keyCode, event -> //Enter key Action
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                //키패드 내리기
                val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(binding.etSearchStart.getWindowToken(), 0)

                //처리
                keyword = binding.etSearchStart.text.toString()
                pageNumber = 1
                searchKeyword(keyword, pageNumber)
                binding.layoutList.visibility = View.VISIBLE
                return@OnKeyListener true
            }
            false
        })

        listAdapter.setItemClickListener(object : ListAdapter.OnItemClickListener {
            override fun onClick(v: View, position: Int) {
                binding.layoutList.visibility = View.GONE
                binding.etSearchStart.setText(listItems[position].name)
            }
        })

    }

    private fun searchKeyword(keyword: String, page: Int) {
        val retrofit = Retrofit.Builder() // Retrofit 구성
            .baseUrl(MainActivity.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val api = retrofit.create(KakaoAPIKeyword::class.java) // 통신 인터페이스를 객체로 생성
        val call = api.getSearchKeyword(MainActivity.API_KEY, keyword,"126.9932215" ,"37.561048", 20000) // 검색 조건 입력

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

}