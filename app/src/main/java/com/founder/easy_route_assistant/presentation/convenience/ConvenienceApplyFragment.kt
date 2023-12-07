package com.founder.easy_route_assistant.presentation.convenience

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import com.founder.easy_route_assistant.R
import com.founder.easy_route_assistant.Utils.MyApplication
import com.founder.easy_route_assistant.Utils.showToast
import com.founder.easy_route_assistant.data.model.request.RequestConvenienceApplyDto
import com.founder.easy_route_assistant.data.service.ServicePool
import com.founder.easy_route_assistant.databinding.BottomSheetConvenienceApplyBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import retrofit2.Call
import retrofit2.Response

class ConvenienceApplyFragment : BottomSheetDialogFragment() {
    private var _binding: BottomSheetConvenienceApplyBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = BottomSheetConvenienceApplyBinding.inflate(inflater, container, false)
        val placeName = arguments?.getString("name")
        binding.tvBsLocation.text = placeName

        val placeAddress = arguments?.getString("address")!!
        binding.tvBsAddressResult.text = placeAddress
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        clickSpinner()
        onClickSave()
        onClickCancel()
    }

    companion object {
        const val TAG = "ConvenienceApplyFragment"
    }

    private fun clickSpinner() {
        val list = listOf("리프트/엘리베이터", "장애인 화장실", "휠체어 충전소")

        binding.spinnerBsConvenienceTypeResult.adapter =
            ConvenienceSpinnerAdapter(requireContext(), R.layout.item_spinner_type_option, list)
        binding.spinnerBsConvenienceTypeResult.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long,
                ) {
                    binding.spinnerBsConvenienceTypeResult.getItemAtPosition(position).toString()
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    context!!.showToast("편의시설 타입을 선택해주세요.")
                }
            }
    }

    private fun saveConvenienceApply() {
        val placeName = arguments?.getString("name")
        binding.tvBsLocation.text = placeName

        val placeAddress = arguments?.getString("address")!!
        binding.tvBsAddressResult.text = placeAddress

        val header = MyApplication.prefs.getString("jwt", "")
        val convenientType = binding.spinnerBsConvenienceTypeResult.getItemAtPosition(0).toString()

        val pointX = arguments?.getDouble("pointX")!!
        val pointY = arguments?.getDouble("pointY")!!
        val point = RequestConvenienceApplyDto.Point(pointX, pointY)

        val content = binding.etBsConvenienceDetailResult.text.toString()

        ServicePool.ConvenienceService.sendConvenienceApply(
            header,
            RequestConvenienceApplyDto(convenientType, point, placeAddress, content),
        )
            .enqueue(object : retrofit2.Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    when (response.code()) {
                        201 -> {
                            context!!.showToast("편의 시설 요청을 완료했습니다.")
                            dismiss()
                        }

                        else -> context!!.showToast("서버 에러 발생")
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    context!!.showToast("네트워크 에러 발생")
                }
            })
    }

    private fun onClickSave() {
        binding.tvBsSaveBtn.setOnClickListener {
            saveConvenienceApply()
        }
    }

    private fun onClickCancel() {
        binding.tvBsCancelBtn.setOnClickListener {
            dismiss()
        }
    }
}
