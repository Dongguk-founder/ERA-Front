package com.founder.easy_route_assistant.presentation.admin

import androidx.recyclerview.widget.RecyclerView
import com.founder.easy_route_assistant.data.model.response.ResponseConvenienceDto
import com.founder.easy_route_assistant.databinding.ItemConvenientBinding

class ConvenientViewHolder(
    private val binding: ItemConvenientBinding,
    private val patchOnClick: (String, String, DoubleArray, String, String, String, Int, Boolean) -> Unit,
) :
    RecyclerView.ViewHolder(binding.root) {
    fun onBind(convenientData: ResponseConvenienceDto.Convenience) {
        binding.tvConvenientType.text = convenientData.convenienceType
        binding.tvConvenientAddress.text = convenientData.convenienceAddress
        binding.tvFavoriteItemDetail.text = convenientData.convenienceContent

        binding.btnConvenientRegister.setOnClickListener {
            val convenientType = convenientData.convenienceType
            val point = doubleArrayOf(convenientData.point.pointX, convenientData.point.pointY)
            val description = binding.etConvenientDescription.text.toString()
            val weekdays = binding.tvConvenientWeekdays.text.toString()
            val saturday = binding.etConvenientSaturday.text.toString()
            val holidays = binding.etConvenientHoliday.text.toString()
            val id = convenientData.identifier
            patchOnClick(convenientType, description, point, weekdays, saturday, holidays, id, true)
        }

        binding.btnConvenientDelete.setOnClickListener {
            val convenientType = convenientData.convenienceType
            val point = doubleArrayOf(convenientData.point.pointX, convenientData.point.pointY)
            val description = binding.etConvenientDescription.text.toString()
            val weekdays = binding.tvConvenientWeekdays.text.toString()
            val saturday = binding.etConvenientSaturday.text.toString()
            val holidays = binding.etConvenientHoliday.text.toString()
            val id = convenientData.identifier
            patchOnClick(
                convenientType,
                description,
                point,
                weekdays,
                saturday,
                holidays,
                id,
                false
            )
        }
    }
}
