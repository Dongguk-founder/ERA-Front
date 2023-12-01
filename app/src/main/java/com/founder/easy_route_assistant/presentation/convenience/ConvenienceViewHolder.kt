package com.founder.easy_route_assistant.presentation.convenience

import androidx.recyclerview.widget.RecyclerView
import com.founder.easy_route_assistant.data.model.response.ResponseConvenienceDto
import com.founder.easy_route_assistant.databinding.ItemUserConvenientBinding

class ConvenienceViewHolder(private val binding: ItemUserConvenientBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun onBind(convenienceData: ResponseConvenienceDto.Convenience) {
        binding.tvConvenientType.text = convenienceData.convenienceType
        binding.tvConvenientAddress.text = convenienceData.convenienceAddress
        binding.tvConvenientDescription.text = convenienceData.convenienceContent
    }
}
