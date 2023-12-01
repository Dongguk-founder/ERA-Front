package com.founder.easy_route_assistant.presentation.auth

import android.content.Intent
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.founder.easy_route_assistant.Utils.MyApplication
import com.founder.easy_route_assistant.Utils.showToast
import com.founder.easy_route_assistant.data.model.request.RequestLoginEmailDto
import com.founder.easy_route_assistant.data.model.response.ResponseLoginEmailDto
import com.founder.easy_route_assistant.data.service.ServicePool.authService
import com.founder.easy_route_assistant.databinding.ActivityLoginEmailBinding
import com.founder.easy_route_assistant.presentation.MainActivity
import retrofit2.Call
import retrofit2.Response

class LoginEmailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginEmailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginEmailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        addOnBackPressedCallback()
        loginClick()
        signup()
    }

    // 로그인 버튼 클릭할 경우
    private fun loginClick() {
        binding.btnLoginSignIn.setOnClickListener {
            val inputID = binding.etLoginId.text.toString()
            val inputPW = binding.etLoginPw.text.toString()

            authService.loginEmail(RequestLoginEmailDto(inputID, inputPW))
                .enqueue(object : retrofit2.Callback<ResponseLoginEmailDto> {
                    override fun onResponse(
                        call: Call<ResponseLoginEmailDto>,
                        response: Response<ResponseLoginEmailDto>,
                    ) {
                        when (response.code()) {
                            202 -> {
                                // 로그인 성공
                                val data: ResponseLoginEmailDto = response.body()!!
                                MyApplication.prefs.setString("jwt", data.token)
                                showToast("로그인이 성공했습니다.")
                                // 로그인 후 메인 액티비티로 전환
                                val nextIntent =
                                    Intent(this@LoginEmailActivity, MainActivity::class.java)
                                nextIntent.putExtra("id", inputID)
                                startActivity(nextIntent)
                            }

                            400 -> {
                                // 로그인 실패
                                showToast("로그인에 실패했습니다.")
                            }

                            else -> {
                                showToast("서버 에러 발생")
                            }
                        }
                    }

                    // 네트워크 요청 중 오류 발생 시
                    override fun onFailure(call: Call<ResponseLoginEmailDto>, t: Throwable) {
                        showToast("네트워크 오류 발생")
                    }
                })
        }
    }

    private fun addOnBackPressedCallback() {
        var backPressedTime: Long = 0
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                // 뒤로 가기 버튼이 눌렸을 때 처리 동작
                if (System.currentTimeMillis() - backPressedTime >= 2000) {
                    backPressedTime = System.currentTimeMillis()
                    showToast("뒤로 버튼을 한번 더 누르면 소셜로그인 창으로 이동합니다.")
                } else if (System.currentTimeMillis() - backPressedTime < 2000) {
                    backPressedTime = 0
                    navigateToLoginActivity()
                }
            }
        }
        this.onBackPressedDispatcher.addCallback(this, callback)
    }

    private fun navigateToLoginActivity() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }

    private fun signup() {
        binding.tvLoginSignUp.setOnClickListener {
            val intent = Intent(this@LoginEmailActivity, SignUpActivity::class.java)
            startActivity(intent)
        }
    }
}
