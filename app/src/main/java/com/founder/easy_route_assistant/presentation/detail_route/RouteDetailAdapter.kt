package com.founder.easy_route_assistant.presentation.detail_route

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.founder.easy_route_assistant.data.model.response.ResponseRouteDetailDto
import com.founder.easy_route_assistant.databinding.ItemRouteDetailBinding

class RouteDetailAdapter(
    context: Context,
    private val clickDetail: (List<ResponseRouteDetailDto.RouteDetail.Descriptions>) -> Unit
) : RecyclerView.Adapter<RouteDetailViewHolder>() {
    private val inflater by lazy { LayoutInflater.from(context) }
    private var routeDetailList: List<ResponseRouteDetailDto.RouteDetail> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RouteDetailViewHolder {
        val binding = ItemRouteDetailBinding.inflate(inflater, parent, false)
        return RouteDetailViewHolder(binding, clickDetail)
    }

    override fun onBindViewHolder(holder: RouteDetailViewHolder, position: Int) {
        holder.onBind(routeDetailList[position], position, routeDetailList.size)
    }

    override fun getItemCount() = routeDetailList.size

    fun setRouteDetailList(routeDetailList_: List<ResponseRouteDetailDto.RouteDetail>?) {
        if (routeDetailList_ != null) {
            routeDetailList = routeDetailList_.toList()
        }
        notifyDataSetChanged()
    }
}
