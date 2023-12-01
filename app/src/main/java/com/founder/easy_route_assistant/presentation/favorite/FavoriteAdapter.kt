package com.founder.easy_route_assistant.presentation.favorite

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.founder.easy_route_assistant.data.model.response.ResponseFavoriteListDto
import com.founder.easy_route_assistant.databinding.ItemFavoriteBinding

class FavoriteAdapter(context: Context) : RecyclerView.Adapter<FavoriteViewHolder>() {
    private val inflater by lazy { LayoutInflater.from(context) }

    private var favoriteList: List<ResponseFavoriteListDto.FavoriteList> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val binding = ItemFavoriteBinding.inflate(inflater, parent, false)
        return FavoriteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.onBind(favoriteList[position])
    }

    override fun getItemCount() = favoriteList.size

    fun setFavoriteList(favoriteList: List<ResponseFavoriteListDto.FavoriteList>) {
        this.favoriteList = favoriteList.toList()
        notifyDataSetChanged()
    }
}
