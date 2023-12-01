package com.founder.easy_route_assistant.presentation.admin

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.founder.easy_route_assistant.data.model.response.ResponseConvenienceDto
import com.founder.easy_route_assistant.databinding.ItemConvenientBinding

class ConvenientAdapter(context: Context) : RecyclerView.Adapter<ConvenientViewHolder>() {
    private val inflater by lazy { LayoutInflater.from(context) }

    private var convenientList: List<ResponseConvenienceDto.Convenience> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConvenientViewHolder {
        val binding = ItemConvenientBinding.inflate(inflater, parent, false)
        return ConvenientViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ConvenientViewHolder, position: Int) {
        holder.onBind(convenientList[position])
    }

    override fun getItemCount() = convenientList.size

    fun setConvenientList(convenientList: List<ResponseConvenienceDto.Convenience>) {
        this.convenientList = convenientList.toList()
        notifyDataSetChanged()
    }
}
