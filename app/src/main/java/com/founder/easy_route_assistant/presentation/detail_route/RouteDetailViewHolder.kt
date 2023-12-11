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

        if(item.moveMode == "SUBWAY"){
            binding.tvItemTitle.text = item.departure + "역"
        } else{
            binding.tvItemTitle.text = item.departure
        }
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
            if (item.moveMode == "SUBWAY"){
                binding.tvItemRouteDetail.text = "${item.timeSecond}, ${item.distanceMeter}개 정류장 이동"
            }
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
                    R.drawable.ic_wheelchair_64
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
                    R.drawable.ic_bus_64
                )
                binding.itemInfoIconOverlay.setImageDrawable(busIcon)
            }

            "SUBWAY" -> {
                // 초기화
                binding.itemInfoCircle.backgroundTintList = ColorStateList.valueOf(
                    ContextCompat.getColor(itemView.context, R.color.white)
                )

                val subwayColor = Color.parseColor(item.routeColor)
                binding.itemInfoCircle.backgroundTintList = ColorStateList.valueOf(subwayColor)

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
                    R.drawable.ic_elevator_64
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
