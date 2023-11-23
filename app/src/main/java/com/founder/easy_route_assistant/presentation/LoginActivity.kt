package com.founder.easy_route_assistant.presentation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.founder.easy_route_assistant.R
import com.founder.easy_route_assistant.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun clickSignIn(){
        val signin = binding.tvLoginEmail
        signin.setOnClickListener{
            val nextIntent = Intent(this, LoginEmailActivity::class.java)
            startActivity(nextIntent)
        }
    }

    private fun clickSignUp(){
        val signup = binding.tvLoginSignup
        signup.setOnClickListener {
            val nextIntent = Intent(this, SignUpActivity::class.java)
            startActivity(nextIntent)
        }
    }
}