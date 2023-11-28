package com.founder.easy_route_assistant.presentation.admin

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.founder.easy_route_assistant.databinding.ActivityAdminBinding

class AdminActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAdminBinding
    private val viewModel by viewModels<ConvenientViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)

        showConvenienceItemList()
    }

    private fun showConvenienceItemList() {
        val convenientAdapter = ConvenientAdapter(this)
        binding.rvAdminConvenientList.adapter = convenientAdapter
        convenientAdapter.setConvenientList(viewModel.mockConvenienceList)
    }
}
