package com.founder.easy_route_assistant.presentation.favorite

import androidx.recyclerview.widget.RecyclerView
import com.founder.easy_route_assistant.databinding.ItemFavoriteBinding

class FavoriteViewHolder(private val binding: ItemFavoriteBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun onBind(favoriteData: Favorite) {
        binding.tvFavoriteItemName.text = favoriteData.favoriteName
        binding.tvFavoriteItemAddress.text = favoriteData.favoriteAddress
    }
}
