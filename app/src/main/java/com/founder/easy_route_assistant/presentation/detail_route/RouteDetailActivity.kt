package com.founder.easy_route_assistant.presentation.detail_route

import android.os.Bundle
import android.util.Log
import android.view.Gravity
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.founder.easy_route_assistant.R
import com.founder.easy_route_assistant.Utils.MyApplication
import com.founder.easy_route_assistant.Utils.showToast
import com.founder.easy_route_assistant.data.model.response.ResponseRouteDetailDto
import com.founder.easy_route_assistant.data.service.ServicePool
import com.founder.easy_route_assistant.databinding.ActivityRouteDetailBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RouteDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRouteDetailBinding
    val id = 3
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRouteDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imvBackBtn.bringToFront()
        binding.imvReloadBtn.bringToFront()

        // 연결할 때 Intent로 클릭한 아이템의 id 받아오기
        setDrawer()
        setRouteDetailItemList()
        addOnBackPressedCallback()
        clickBackBtn()
        reloadClick()
    }

    private fun setDrawer() {
        val drawer = binding.layoutDrawer
        drawer.openDrawer(Gravity.LEFT)
    }

    private fun setRouteDetailItemList() {
        val id = intent.getIntExtra("id", 1)
        val header = MyApplication.prefs.getString("jwt", "")
        ServicePool.routeDetailService.getRouteDetail(header, id)
            .enqueue(object : Callback<ResponseRouteDetailDto> {
                override fun onResponse(
                    call: Call<ResponseRouteDetailDto>,
                    response: Response<ResponseRouteDetailDto>
                ) {
                    when (response.code()) {
                        202 -> {
                            showToast("상세 경로 안내입니다.")
                            val data: ResponseRouteDetailDto = response.body()!!
                            Log.d("Test", "Body: ${response.body()}")
                            setRouteDetailItemAdapter(data.routeDetails)
                            binding.tvTotalTime.text = data.totalTime
                        }

                        else -> showToast("서버 에러 발생")
                    }
                }

                override fun onFailure(call: Call<ResponseRouteDetailDto>, t: Throwable) {
                    showToast("네트워크 오류 발생")
                }
            })
    }

    private fun setRouteDetailItemAdapter(routeDetailItemList: List<ResponseRouteDetailDto.RouteDetail>?) {
        val routeDetailItemAdapter = RouteDetailAdapter(this, ::clickDetail)
        routeDetailItemAdapter.setRouteDetailList(routeDetailItemList)
        binding.rvRouteDetail.adapter = routeDetailItemAdapter
    }

    private fun clickDetail(description: List<ResponseRouteDetailDto.RouteDetail.Descriptions>) {
        val bundle = Bundle()
        when (description.size) {
            1 -> {
                bundle.putString("imgPath1", description[0].imgPath)
                bundle.putString("description1", description[0].descriptions.toString())
            }

            2 -> {
                bundle.putString("imgPath1", description[0].imgPath)
                bundle.putString("description1", description[0].descriptions.toString())
                bundle.putString("imgPath2", description[1].imgPath)
                bundle.putString("description2", description[1].descriptions.toString())
            }

            3 -> {
                bundle.putString("imgPath1", description[0].imgPath)
                bundle.putString("description1", description[0].descriptions.toString())
                bundle.putString("imgPath2", description[1].imgPath)
                bundle.putString("description2", description[1].descriptions.toString())
                bundle.putString("imgPath3", description[2].imgPath)
                bundle.putString("description3", description[2].descriptions.toString())
            }

            4 -> {
                bundle.putString("imgPath1", description[0].imgPath)
                bundle.putString("description1", description[0].descriptions.toString())
                bundle.putString("imgPath2", description[1].imgPath)
                bundle.putString("description2", description[1].descriptions.toString())
                bundle.putString("imgPath3", description[2].imgPath)
                bundle.putString("description3", description[2].descriptions.toString())
                bundle.putString("imgPath4", description[3].imgPath)
                bundle.putString("description4", description[3].descriptions.toString())
            }
        }

        val drawer = binding.layoutDrawer
        drawer.closeDrawer(Gravity.LEFT)

        val routeDetailFragment = RouteDetailFragment()
        routeDetailFragment.arguments = bundle

        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, routeDetailFragment)
        transaction.commit()
    }

    private fun addOnBackPressedCallback() {
        var backPressedTime: Long = 0
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                // 뒤로 가기 버튼이 눌렸을 때 처리 동작
                if (System.currentTimeMillis() - backPressedTime >= 2000) {
                    backPressedTime = System.currentTimeMillis()
                    showToast("뒤로 버튼을 한번 더 누르면 경로안내로 이동합니다.")
                } else if (System.currentTimeMillis() - backPressedTime < 2000) {
                    backPressedTime = 0
                    finish();
                }
            }
        }
        this.onBackPressedDispatcher.addCallback(this, callback)
    }

    private fun clickBackBtn() {
        val drawer = binding.layoutDrawer
        binding.imvBackBtn.setOnClickListener {
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
            drawer.closeDrawer(Gravity.LEFT)
        }
    }

    private fun reloadClick() {
        binding.imvReloadBtn.setOnClickListener {
            setRouteDetailItemList()
        }
    }
}