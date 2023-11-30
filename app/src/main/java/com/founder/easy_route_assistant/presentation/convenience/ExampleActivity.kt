package com.founder.easy_route_assistant.presentation.convenience

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.founder.easy_route_assistant.databinding.ActivityExampleBinding

class ExampleActivity : AppCompatActivity() {
    private lateinit var binding: ActivityExampleBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExampleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        showConvenienceApply()
    }

    private fun showConvenienceApply() {
        binding.exampleBtn.setOnClickListener {
            ConvenienceApplyFragment().show(supportFragmentManager, ConvenienceApplyFragment.TAG)
        }
    }
}
