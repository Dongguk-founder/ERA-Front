package com.founder.easy_route_assistant.presentation.convenience

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.founder.easy_route_assistant.databinding.BottomSheetConvenienceApplyBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ConvenienceApplyFragment : BottomSheetDialogFragment() {
    private var _binding: BottomSheetConvenienceApplyBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = BottomSheetConvenienceApplyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*val list = listOf("리프트/엘리베이터, 장애인 화장실, 휠체어 충전소")

        binding.tvBsConvenienceTypeResult.adapter =
            ConvenienceSpinnerAdapter(requireContext(), R.layout.item_spinner_type_option, list)
        binding.tvBsConvenienceTypeResult.onItemClickListener =
            object : AdapterView.OnItemSelectedListener, AdapterView.OnItemClickListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long,
                ) {
                    val value =
                        binding.tvBsConvenienceTypeResult.getItemAtPosition(position).toString()
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    context.showToast("타입을 선택해주세요.")
                }
            }*/
    }

    companion object {
        const val TAG = "ConvenienceApplyFragment"
    }
}
