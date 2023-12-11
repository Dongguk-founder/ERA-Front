package com.founder.testrecyclerview

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.founder.easy_route_assistant.data.model.response.RouteElements
import com.founder.easy_route_assistant.databinding.ItemRouteElementBinding

class routeElementsAdapter(val items: List<RouteElements>) :
    RecyclerView.Adapter<routeElementsAdapter.ViewHolder>() {


    class ViewHolder(private val binding: ItemRouteElementBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: RouteElements) {
            binding.tvListNumber.text = item.line
            val bgShape = binding.itemBig.background as GradientDrawable
            bgShape.setColor(Color.parseColor(item.routeColor))

            if (item.line == null) {
                binding.tvListName.text = item.start
                binding.ivBus.visibility = View.VISIBLE
                binding.tvListRoad.text = item.name
            } else {
                binding.tvListName.text = item.start + "역"
                binding.ivBus.visibility = View.GONE
                binding.tvListRoad.text = item.name + "방면"
            }

            if (item.mode == null) {
                binding.itemSmall.visibility = View.VISIBLE
                binding.itemWhite.visibility = View.VISIBLE
                binding.itemBig.visibility = View.GONE
                binding.viewBottom.visibility = View.VISIBLE
                binding.ivBus.visibility = View.GONE
                binding.tvListRoad.text = " "
                val bgShape = binding.itemSmall.background as GradientDrawable
                bgShape.setColor(Color.parseColor(item.routeColor))
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): routeElementsAdapter.ViewHolder =
        ViewHolder(
            ItemRouteElementBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: routeElementsAdapter.ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}