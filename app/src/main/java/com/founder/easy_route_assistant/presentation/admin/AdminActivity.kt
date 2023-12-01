package com.founder.easy_route_assistant.presentation.admin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.founder.easy_route_assistant.Utils.MyApplication
import com.founder.easy_route_assistant.Utils.showToast
import com.founder.easy_route_assistant.data.model.response.ResponseConvenienceDto
import com.founder.easy_route_assistant.data.service.ServicePool
import com.founder.easy_route_assistant.databinding.ActivityAdminBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdminActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAdminBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setConvenientItemList()
    }

    private fun setConvenientItemList() {
        val header = MyApplication.prefs.getString("jwt", "")
        ServicePool.ConvenienceService.getConvenienceList(header)
            .enqueue(object : Callback<ResponseConvenienceDto> {
                override fun onResponse(
                    call: Call<ResponseConvenienceDto>,
                    response: Response<ResponseConvenienceDto>,
                ) {
                    if (response.isSuccessful) {
                        if (response.code() == 202) {
                            val data: ResponseConvenienceDto = response.body()!!
                            setConvenientAdapter(data.convenienceList)
                            showToast("성공!")
                        } else {
                            showToast("서버 에러 발생")
                        }
                    }
                }

                override fun onFailure(call: Call<ResponseConvenienceDto>, t: Throwable) {
                    showToast("네트워크 에러 발생")
                }
            })
    }

    private fun setConvenientAdapter(convenientList: List<ResponseConvenienceDto.Convenience>) {
        val convenientAdapter = ConvenientAdapter(this)
        convenientAdapter.setConvenientList(convenientList)
        binding.rvAdminConvenientList.adapter = convenientAdapter
    }
}
