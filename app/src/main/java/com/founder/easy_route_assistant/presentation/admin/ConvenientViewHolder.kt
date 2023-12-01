package com.founder.easy_route_assistant.presentation.admin

import androidx.recyclerview.widget.RecyclerView
import com.founder.easy_route_assistant.data.model.response.ResponseConvenienceDto
import com.founder.easy_route_assistant.databinding.ItemConvenientBinding

class ConvenientViewHolder(private val binding: ItemConvenientBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun onBind(convenientData: ResponseConvenienceDto.Convenience) {
        binding.tvConvenientType.text = convenientData.convenienceType
        binding.tvConvenientAddress.text = convenientData.convenienceAddress
        binding.tvFavoriteItemDetail.text = convenientData.convenienceContent
    }
}
