package com.founder.easy_route_assistant.presentation.favorite

import androidx.recyclerview.widget.RecyclerView
import com.founder.easy_route_assistant.data.model.response.ResponseFavoriteListDto
import com.founder.easy_route_assistant.databinding.ItemFavoriteBinding

class FavoriteViewHolder(private val binding: ItemFavoriteBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun onBind(favoriteData: ResponseFavoriteListDto.FavoriteList) {
        binding.tvFavoriteItemName.text = favoriteData.placeName
        binding.tvFavoriteItemAddress.text = favoriteData.placeAddress

        onclickBack()
    }

    private fun onclickBack() {
        binding.imvDeleteFavoriteItem.setOnClickListener {

        }
    }
}
