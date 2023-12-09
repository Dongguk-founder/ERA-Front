package com.founder.testrecyclerview

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.founder.easy_route_assistant.R
import com.founder.easy_route_assistant.presentation.RouteListLayout

class RouteDTOAdapter(private val items: ArrayList<RouteListLayout>) : RecyclerView.Adapter<RouteDTOAdapter.ViewHolder>() {

    private var itemClickListener: OnItemClickListener? = null



    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val totaltime: TextView = itemView.findViewById(R.id.tv_palette)
        val element: RecyclerView = itemView.findViewById(R.id.rv_color)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RouteDTOAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_route, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.totaltime.text = items[position].totalTime
        val item = items[position].routeElements[1]
        holder.element.apply {
            adapter = routeElementsAdapter(items[position].routeElements)
            layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }
        routeElementsAdapter(items[position].routeElements).notifyDataSetChanged()

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