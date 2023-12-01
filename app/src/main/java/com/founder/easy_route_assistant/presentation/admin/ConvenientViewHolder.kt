package com.founder.easy_route_assistant.presentation.admin

import androidx.recyclerview.widget.RecyclerView
import com.founder.easy_route_assistant.databinding.ItemConvenientBinding

class ConvenientViewHolder(private val binding: ItemConvenientBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun onBind(convenientData: Convenient) {
        binding.tvConvenientType.text = convenientData.convenientType
        binding.tvFavoriteItemDetail.text = convenientData.convenientDetail
        binding.tvConvenientAddress.text = convenientData.convenientAddress
        binding.tvConvenientWeekdays.hint = convenientData.convenientWeekdays
        binding.etConvenientSaturday.hint = convenientData.convenientSaturday
        binding.etConvenientHoliday.hint = convenientData.convenientHoliday
    }
}
