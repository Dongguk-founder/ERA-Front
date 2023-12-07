package com.founder.easy_route_assistant.presentation.detail_route

import android.content.ClipDescription
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.davemorrissey.labs.subscaleview.ImageSource
import com.davemorrissey.labs.subscaleview.ImageSource.bitmap
import com.davemorrissey.labs.subscaleview.ImageSource.uri
import com.founder.easy_route_assistant.Utils.MyApplication
import com.founder.easy_route_assistant.Utils.showToast
import com.founder.easy_route_assistant.data.model.response.ResponseRouteDetailDto
import com.founder.easy_route_assistant.data.service.ServicePool
import com.founder.easy_route_assistant.databinding.ActivityRouteDetailBinding
import com.founder.easy_route_assistant.presentation.route.RouteTabActivity
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

        // Intent로 클릭한 아이템의 id 받아오기

        setRouteDetailItemList()
        addOnBackPressedCallback()
        clickBackBtn()
    }

    private fun setRouteDetailItemList() {
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
                            setRouteDetailItemAdapter(data.routeDetails)
                        }
                        else -> showToast("서버 에러 발생")
                    }
                }

                override fun onFailure(call: Call<ResponseRouteDetailDto>, t: Throwable) {
                    showToast("네트워크 오류 발생")
                }
            })
    }

    private fun setRouteDetailItemAdapter(routeDetailItemList: List<ResponseRouteDetailDto.RouteDetail>){
        val routeDetailItemAdapter = RouteDetailAdapter(this, ::clickDetail)
        routeDetailItemAdapter.setRouteDetailList(routeDetailItemList)
        binding.rvRouteDetail.adapter = routeDetailItemAdapter
    }

    private fun clickDetail(imgPath: String, description: List<String>){
        Glide.with(this)
            .asBitmap()
            .load(uri(imgPath))
            .into(object: CustomTarget<Bitmap>(){
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    binding.imvItemDetailViewImg.setImage(bitmap(resource))
                }

                override fun onLoadCleared(placeholder: Drawable?) {}
            })

        binding.tvItemDetailViewDescription.text = description.toString()
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

    private fun clickBackBtn(){
        binding.btnItemDetailViewExit.setOnClickListener { finish() }
    }
}