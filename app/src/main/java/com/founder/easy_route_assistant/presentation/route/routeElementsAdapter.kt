package com.founder.testrecyclerview

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.founder.easy_route_assistant.R
import com.founder.easy_route_assistant.data.model.response.RouteElements
import com.founder.easy_route_assistant.presentation.ElementListLayout

class routeElementsAdapter(private var items: List<ElementListLayout>) : RecyclerView.Adapter<routeElementsAdapter.ViewHolder>() {


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val start: TextView = itemView.findViewById(R.id.tv_list_name)
        val name: TextView = itemView.findViewById(R.id.tv_list_road)
        val line: TextView = itemView.findViewById(R.id.tv_list_number)
        val imageBig: ImageView = itemView.findViewById(R.id.item_big)
        val imagesmall: ImageView = itemView.findViewById(R.id.item_small)
        val imagebus: ImageView = itemView.findViewById(R.id.iv_bus)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): routeElementsAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_route_element, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: routeElementsAdapter.ViewHolder, position: Int) {
        holder.start.text = items[position].start
        holder.name.text = items[position].name
        holder.line.text = items[position].line
        val bgShape = holder.imageBig.background as GradientDrawable
        bgShape.setColor(Color.parseColor(items[position].routeColor))


        if(items[position].mode == ""){
            holder.imagesmall.visibility= View.VISIBLE
            holder.imageBig.visibility= View.INVISIBLE
            val bgShape = holder.imagesmall.background as GradientDrawable
            bgShape.setColor(Color.parseColor(items[position].routeColor))
        }

        if(items[position].line == ""){
            holder.imagebus.visibility= View.VISIBLE
        }
    }

    override fun getItemCount(): Int = items.size
}