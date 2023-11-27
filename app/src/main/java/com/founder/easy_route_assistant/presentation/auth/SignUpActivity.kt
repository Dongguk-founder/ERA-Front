package com.founder.easy_route_assistant.presentation.auth

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.founder.easy_route_assistant.R
import com.founder.easy_route_assistant.Utils.showToast
import com.founder.easy_route_assistant.data.model.request.RequestSignUpDto
import com.founder.easy_route_assistant.data.service.ServicePool.authService
import com.founder.easy_route_assistant.databinding.ActivitySignUpBinding
import retrofit2.Call
import retrofit2.Response

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        availableSignUp()
        addOnBackPressedCallback()
        clickSignUpButton()
    }

    // 회원가입 가능 조건 검사
    private fun testInfo(): Boolean {
        val id = binding.etSignUpId.text.toString()
        val pw = binding.etSignUpPw.text.toString()
        val pwCheck = binding.etSignUpPwRe.text.toString()
        val nickname = binding.etSignUpName.text.toString()
        val email = binding.etSignUpEmail.text.toString()

        return !(
            id.isEmpty() || pw.isEmpty() || nickname.isEmpty() || email.isEmpty() ||
                pw != pwCheck || nickname.length !in 1..10
            )
    }

    // 회원가입 버튼 색상 변경
    private fun updateButtonState() {
        if (testInfo() && binding.tvSignUpCheck.text == "  확인되었습니다.") {
            binding.btnSignUp.isEnabled = true
            binding.btnSignUp.setBackgroundResource(R.color.soft_green)
        } else {
            binding.btnSignUp.isEnabled = false
            binding.btnSignUp.setBackgroundResource(R.color.gray)
        }
    }

    // 비밀번호 재확인
    private fun updatePWState() {
        // 비밀번호와 입력 정보 확인, 버튼 활성화
        val pwCheck = binding.etSignUpPwRe
        val pw = binding.etSignUpPw
        val check = binding.tvSignUpCheck

        pwCheck.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                if (pw.text.toString() == pwCheck.text.toString()) {
                    check.text = "  확인되었습니다."
                    check.setTextColor(Color.parseColor("#51CD3D"))
                    updateButtonState()
                } else {
                    check.text = "  비밀번호가 다릅니다."
                    check.setTextColor(Color.parseColor("#A4A4A4"))
                    updateButtonState()
                }
            }
        })
    }

    // 회원가입 가능 여부 실시간 확인
    private fun availableSignUp() {
        // 비밀번호와 입력 정보 확인, 버튼 활성화
        val id = binding.etSignUpId
        val pw = binding.etSignUpPw
        val pwCheck = binding.etSignUpPwRe
        val name = binding.etSignUpName
        val email = binding.etSignUpEmail

        val textWatchers = arrayOf(
            id,
            pw,
            pwCheck,
            name,
            email,
        )

        // 모든 입력 필드에 대해 TextWatcher를 설정하여 변경을 감지하고 상태를 업데이트
        for (textWatcher in textWatchers) {
            if (textWatcher == pwCheck) {
                updatePWState()
            } else {
                textWatcher.addTextChangedListener(object : TextWatcher {
                    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                        updateButtonState()
                    }

                    override fun afterTextChanged(p0: Editable?) {
                        updateButtonState()
                    }
                })
            }
        }
    }

    // 회원가입 버튼 클릭 시
    private fun clickSignUpButton() {
        val id = binding.etSignUpId
        val pw = binding.etSignUpPw
        val name = binding.etSignUpName
        val email = binding.etSignUpEmail
        val signUpButton = binding.btnSignUp

        // 회원가입 버튼 클릭 시
        signUpButton.setOnClickListener {
            authService.signUp(
                RequestSignUpDto(
                    id.toString(),
                    pw.toString(),
                    name.toString(),
                    email.toString(),
                ),
            )
                .enqueue(object : retrofit2.Callback<Response<Void>> {
                    override fun onResponse(
                        call: Call<Response<Void>>,
                        response: Response<Response<Void>>,
                    ) {
                        when (response.code()) {
                            201 -> {
                                // 회원가입 성공
                                showToast("회원가입 성공!")
                                val intent =
                                    Intent(this@SignUpActivity, LoginEmailActivity::class.java)
                                startActivity(intent)
                            }

                            400 -> {
                                // 회원가입 실패
                                showToast("이미 존재하는 아이디입니다.")
                            }

                            else -> {
                                showToast("서버 에러 발생")
                            }
                        }
                    }

                    override fun onFailure(call: Call<Response<Void>>, t: Throwable) {
                        showToast("네트워크 에러 발생")
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
                } else {
                    finish()
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
}
