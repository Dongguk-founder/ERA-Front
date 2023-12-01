package com.founder.easy_route_assistant.presentation.convenience

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.founder.easy_route_assistant.data.model.response.ResponseConvenienceDto
import com.founder.easy_route_assistant.databinding.ItemUserConvenientBinding

class ConvenienceAdapter(context: Context) : RecyclerView.Adapter<ConvenienceViewHolder>() {
    private val inflater by lazy { LayoutInflater.from(context) }

    private var convenienceList: List<ResponseConvenienceDto.Convenience> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConvenienceViewHolder {
        val binding = ItemUserConvenientBinding.inflate(inflater, parent, false)
        return ConvenienceViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ConvenienceViewHolder, position: Int) {
        holder.onBind(convenienceList[position])
    }

    override fun getItemCount() = convenienceList.size

    fun setConvenientList(convenienceList: List<ResponseConvenienceDto.Convenience>) {
        this.convenienceList = convenienceList.toList()
        notifyDataSetChanged()
    }
}
