package com.founder.easy_route_assistant.presentation.convenience

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.founder.easy_route_assistant.Utils.MyApplication
import com.founder.easy_route_assistant.Utils.showToast
import com.founder.easy_route_assistant.data.model.response.ResponseConvenienceDto
import com.founder.easy_route_assistant.data.service.ServicePool
import com.founder.easy_route_assistant.databinding.ActivityConvenienceListBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ConvenienceListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityConvenienceListBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConvenienceListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setConvenienceList()
        onClickBackBtn()
    }

    private fun setConvenienceList() {
        val header = MyApplication.prefs.getString("jwt", "")
        ServicePool.ConvenienceService.getConvenienceList(header)
            .enqueue(object : Callback<ResponseConvenienceDto> {
                override fun onResponse(
                    call: Call<ResponseConvenienceDto>,
                    response: Response<ResponseConvenienceDto>,
                ) {
                    when (response.code()) {
                        202 -> {
                            val data: ResponseConvenienceDto = response.body()!!
                            setConvenienceAdapter(data.convenienceList)
                            showToast("신청한 편의시설 목록입니다.")
                        }
                        else -> showToast("서버 에러 발생")
                    }
                }

                override fun onFailure(call: Call<ResponseConvenienceDto>, t: Throwable) {
                    showToast("네트워크 오류 발생")
                }
            })
    }

    private fun setConvenienceAdapter(convenienceList: List<ResponseConvenienceDto.Convenience>) {
        val convenienceAdapter = ConvenienceAdapter(this)
        convenienceAdapter.setConvenientList(convenienceList)
        binding.rvUserConvenientList.adapter = convenienceAdapter
    }

    private fun onClickBackBtn() {
        binding.btnConvenientBack.setOnClickListener {
            finish()
        }
    }
}
