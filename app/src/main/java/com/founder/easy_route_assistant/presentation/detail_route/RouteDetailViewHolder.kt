package com.founder.easy_route_assistant.presentation.detail_route

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.widget.ImageViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.founder.easy_route_assistant.R
import com.founder.easy_route_assistant.data.model.response.ResponseRouteDetailDto
import com.founder.easy_route_assistant.databinding.ItemRouteDetailBinding

class RouteDetailViewHolder(
    private val binding: ItemRouteDetailBinding,
    private val clickDetail: (String, List<String>) -> Unit,
) :
    RecyclerView.ViewHolder(binding.root) {
    fun onBind(item: ResponseRouteDetailDto.RouteDetail, position: Int, itemCount: Int) {
        binding.tvItemTitle.text = item.departure
        binding.tvItemRouteDetail.text = "${item.timeSecond / 60}분, ${item.distanceMeter}m 이동"
        binding.tvItemLineNumber.text = item.subwayNum

        if (item.moveMode == "elevator") {
            R.id.tv_item_route_img = View.VISIBLE
        }

        setLineVisible(item, position, itemCount)
        coloringIcon(item)
        clickDetailRoute(item)
    }

    private fun coloringIcon(item: ResponseRouteDetailDto.RouteDetail){
        when(item.moveMode){
            "WALK" -> {
                val walkIcon = ContextCompat.getDrawable(itemView.context, R.drawable.baseline_accessible_24)
                val wrappedWalkIcon = DrawableCompat.wrap(walkIcon!!)
                DrawableCompat.setTint(wrappedWalkIcon, ContextCompat.getColor(itemView.context, R.color.wheelchair_gray))

                binding.itemInfoCircle.setImageDrawable(wrappedWalkIcon)
            }
            "BUS" -> {
                val busIcon = ContextCompat.getDrawable(itemView.context, R.drawable.baseline_directions_bus_24)
                val wrappedBusIcon = DrawableCompat.wrap(busIcon!!)
                DrawableCompat.setTint(wrappedBusIcon, ContextCompat.getColor(itemView.context, R.color.bus_green))

                binding.itemInfoCircle.setImageDrawable(wrappedBusIcon)
            }
            "SUBWAY" -> {
                val subwayColorCode = item.routeColor
                val subwayColor = Color.parseColor("#$subwayColorCode")
                ImageViewCompat.setImageTintList(binding.itemInfoCircle, ColorStateList.valueOf(subwayColor))
                binding.tvItemLineNumber.text = item.subwayNum
            }
            "elevator" -> {
                val elevatorIcon = ContextCompat.getDrawable(itemView.context, R.drawable.ic_elevator_28) // ic_walk는 PNG로 저장된 이미지
                val wrappedElevatorIcon = DrawableCompat.wrap(elevatorIcon!!)
                DrawableCompat.setTint(wrappedElevatorIcon, ContextCompat.getColor(itemView.context, R.color.elevator_yellow))

                binding.itemInfoCircle.setImageDrawable(wrappedElevatorIcon)
            }
        }
    }

    private fun clickDetailRoute(item: ResponseRouteDetailDto.RouteDetail){
        binding.tvItemRouteImg.setOnClickListener {
            R.id.layout_item_detail_view = View.VISIBLE
            clickDetail(item.description[0].imgPath, item.description[0].descriptions)
        }
    }

    private fun setLineVisible(item: ResponseRouteDetailDto.RouteDetail, position: Int, itemCount: Int){
        when(position){
            0 -> R.id.line_vertical = View.INVISIBLE
            itemCount-1 -> R.id.line_horizontal = View.INVISIBLE
        }
    }
}
