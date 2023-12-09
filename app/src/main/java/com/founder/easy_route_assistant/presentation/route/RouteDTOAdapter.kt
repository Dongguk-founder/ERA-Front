package com.founder.testrecyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.founder.easy_route_assistant.data.model.response.RouteDTO
import com.founder.easy_route_assistant.databinding.ItemRouteBinding

class RouteDTOAdapter(val items: List<RouteDTO>) : RecyclerView.Adapter<RouteDTOAdapter.ViewHolder>() {

    private var itemClickListener: OnItemClickListener? = null


    class ViewHolder(private val binding: ItemRouteBinding, val context: Context) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: RouteDTO) {
            with(binding)
            {
                tvPalette.text = item.totalTime
                rvColor.apply {
                    adapter = routeElementsAdapter(item.routeElements)
                    layoutManager =
                        LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RouteDTOAdapter.ViewHolder {
        return ViewHolder(ItemRouteBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            parent.context
        )
    }

    override fun onBindViewHolder(holder: RouteDTOAdapter.ViewHolder, position: Int) {
        holder.bind(items[position])

        // 아이템 클릭 이벤트
        holder.itemView.setOnClickListener {
            itemClickListener?.onClick(it, position)
        }
    }

    override fun getItemCount(): Int = items.size

    interface OnItemClickListener {
        fun onClick(view: View,position: Int)
    }

    fun setItemClickListener(onItemClickListener: OnItemClickListener?) {
        this.itemClickListener = onItemClickListener
    }



}