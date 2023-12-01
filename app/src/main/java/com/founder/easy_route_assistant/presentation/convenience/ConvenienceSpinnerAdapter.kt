package com.founder.easy_route_assistant.presentation.convenience

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.annotation.LayoutRes
import com.founder.easy_route_assistant.databinding.ItemSpinnerTypeOptionBinding

class ConvenienceSpinnerAdapter(
    context: Context,
    @LayoutRes private val resId: Int,
    private val menuList: List<String>,
) : ArrayAdapter<String>(context, resId, menuList) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val binding =
            ItemSpinnerTypeOptionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        binding.tvSpinner.text = menuList[position]
        return binding.root
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val binding =
            ItemSpinnerTypeOptionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        binding.tvSpinner.text = menuList[position]

        return binding.root
    }

    override fun getCount() = menuList.size
}
