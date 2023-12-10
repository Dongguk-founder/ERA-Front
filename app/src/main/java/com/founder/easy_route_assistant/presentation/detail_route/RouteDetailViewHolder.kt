package com.founder.easy_route_assistant.presentation.detail_route

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.founder.easy_route_assistant.R
import com.founder.easy_route_assistant.data.model.response.ResponseRouteDetailDto
import com.founder.easy_route_assistant.databinding.ItemRouteDetailBinding

class RouteDetailViewHolder(
    private val binding: ItemRouteDetailBinding,
    private val clickDetail: (List<ResponseRouteDetailDto.RouteDetail.Descriptions>) -> Unit,
) :
    RecyclerView.ViewHolder(binding.root) {
    fun onBind(item: ResponseRouteDetailDto.RouteDetail, position: Int, itemCount: Int) {
        binding.tvItemTitle.text = item.departure
        binding.tvItemLineNumber.text = item.subwayNum

        setVisibleText(item)
        setLineVisible(position, itemCount)
        coloringIcon(item)
    }

    private fun setVisibleText(item: ResponseRouteDetailDto.RouteDetail) {
        if (item.moveMode == "elevator" && item.description != null) {
            binding.tvItemRouteImg.visibility = View.VISIBLE
            clickDetailRoute(item.description)
        }

        if (item.moveMode == "elevator" && item.departure == null && item.arrival == null) {
            binding.tvItemRouteDetail.visibility = View.INVISIBLE
            binding.tvItemTitle.text = "엘리베이터 탑승"
        }

        if (item.moveMode == "BUS" && item.busMessage1 != null && item.busMessage2 != null)
            binding.tvItemRouteDetail.text =
                item.detail + " " + item.busMessage1 + "\n" + item.detail + " " + item.busMessage2
        else if (item.timeSecond == "0분" || item.distanceMeter?.toInt() == 0) {
            binding.tvItemRouteDetail.visibility = View.INVISIBLE
        } else {
            binding.tvItemRouteDetail.text = "${item.timeSecond}, ${item.distanceMeter}m 이동"
        }
    }

    private fun coloringIcon(item: ResponseRouteDetailDto.RouteDetail) {
        when (item.moveMode) {
            "WALK" -> {
                // 초기화
                binding.itemInfoCircle.backgroundTintList = ColorStateList.valueOf(
                    ContextCompat.getColor(itemView.context, R.color.white)
                )

                // 배경 색상 입히기
                binding.itemInfoCircle.backgroundTintList = ColorStateList.valueOf(
                    ContextCompat.getColor(itemView.context, R.color.wheelchair_gray)
                )

                // 아이콘 올리기
                val walkImage = ContextCompat.getDrawable(
                    itemView.context,
                    R.drawable.baseline_accessible_24
                )
                binding.itemInfoIconOverlay.setImageDrawable(walkImage)
            }

            "BUS" -> {
                // 초기화
                binding.itemInfoCircle.backgroundTintList = ColorStateList.valueOf(
                    ContextCompat.getColor(itemView.context, R.color.white)
                )

                binding.itemInfoCircle.backgroundTintList = ColorStateList.valueOf(
                    ContextCompat.getColor(itemView.context, R.color.bus_green)
                )

                val busIcon = ContextCompat.getDrawable(
                    itemView.context,
                    R.drawable.baseline_directions_bus_24
                )
                binding.itemInfoIconOverlay.setImageDrawable(busIcon)

                /*val wrappedBusIcon = DrawableCompat.wrap(busIcon!!)
                DrawableCompat.setTint(
                    wrappedBusIcon,
                    ContextCompat.getColor(itemView.context, R.color.bus_green)
                )*/
            }

            "SUBWAY" -> {
                // 초기화
                binding.itemInfoCircle.backgroundTintList = ColorStateList.valueOf(
                    ContextCompat.getColor(itemView.context, R.color.white)
                )

                var bgShape = binding.itemInfoCircle.background as GradientDrawable
                bgShape.setColor(Color.parseColor(item.routeColor))
                binding.tvItemLineNumber.text = item.subwayNum
            }

            "elevator" -> {
                // 초기화
                binding.itemInfoCircle.backgroundTintList = ColorStateList.valueOf(
                    ContextCompat.getColor(itemView.context, R.color.white)
                )

                binding.itemInfoCircle.backgroundTintList = ColorStateList.valueOf(
                    ContextCompat.getColor(itemView.context, R.color.elevator_yellow)
                )

                val elevatorIcon = ContextCompat.getDrawable(
                    itemView.context,
                    R.drawable.ic_elevator_28
                )
                binding.itemInfoIconOverlay.setImageDrawable(elevatorIcon)
            }
        }
    }

    private fun clickDetailRoute(description: List<ResponseRouteDetailDto.RouteDetail.Descriptions>) {
        binding.tvItemRouteImg.setOnClickListener {
            clickDetail(description)
        }
    }

    private fun setLineVisible(
        position: Int,
        itemCount: Int
    ) {
        when (position) {
            0 -> binding.lineVertical.visibility = View.INVISIBLE
            itemCount - 1 -> binding.lineHorizontal.visibility = View.INVISIBLE
        }
    }
}
