package com.founder.easy_route_assistant.presentation.convenience

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.founder.easy_route_assistant.Utils.MyApplication
import com.founder.easy_route_assistant.Utils.showToast
import com.founder.easy_route_assistant.data.model.response.ResponseConvenienceDto
import com.founder.easy_route_assistant.data.service.ServicePool
import com.founder.easy_route_assistant.databinding.ActivityConvenienceListBinding
import retrofit2.Call
import retrofit2.Response

class ConvenienceListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityConvenienceListBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConvenienceListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setConvenienceList()
    }

    private fun setConvenienceList() {
        val header = MyApplication.prefs.getString("jwt", "")
        ServicePool.ConvenienceService.getConvenienceList(header)
            .enqueue(object : retrofit2.Callback<ResponseConvenienceDto> {
                override fun onResponse(
                    call: Call<ResponseConvenienceDto>,
                    response: Response<ResponseConvenienceDto>,
                ) {
                    if (response.isSuccessful) {
                        if (response.code() == 202) {
                            val data: ResponseConvenienceDto = response.body()!!
                            setConvenienceAdapter(data.convenienceList)
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

    private fun setConvenienceAdapter(convenienceList: List<ResponseConvenienceDto.Convenience>) {
        val convenienceAdapter = ConvenienceAdapter(this)
        convenienceAdapter.setConvenientList(convenienceList)
        binding.rvUserConvenientList.adapter = convenienceAdapter
    }

    /*private fun setReservationList() {
        ServicePool.reservationListService.getReservationList()
            .enqueue(object : retrofit2.Callback<ResponseReservationDto> {
                override fun onResponse(
                    call: Call<ResponseReservationDto>,
                    response: Response<ResponseReservationDto>,
                ) {
                    if (response.isSuccessful) {
                        val data: ResponseReservationDto = response.body()!!
                        setReservationAdapter(data.reservationList)
                        if (response.code() == 500) {
                            context!!.showToast("서버 에러 발생")
                        }
                    }
                }

                override fun onFailure(call: Call<ResponseReservationDto>, t: Throwable) {
                    context!!.showToast("네트워크 에러 발생")
                }
            })
    }

    private fun setReservationAdapter(reserveList: List<ResponseReservationDto.Reservation>) {
        val reservationAdapter = ReservationAdapter(requireContext(), ::patchApplyCode)
        reservationAdapter.setReservationList(reserveList)
        binding.rvReservationItems.adapter = reservationAdapter
    }*/
}
