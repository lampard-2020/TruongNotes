package com.notes.miniapp.main.signin

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.notes.miniapp.databinding.ActivitySignInBinding
import com.notes.miniapp.extension.viewBinding
import com.notes.miniapp.main.base.BaseActivity
import com.notes.miniapp.main.common.WelcomeActivity
import com.notes.miniapp.main.signup.SignUpActivity
import com.notes.miniapp.utils.ResponseData

class SignInActivity : BaseActivity() {

    private val binding by viewBinding(ActivitySignInBinding::inflate)
    private val viewModel: SignInViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initView()
        dataObserver()
    }

    private fun initView() {
        binding.butLogin.setOnClickListener {
            val email = normalizeData(binding.edtAccount.text.toString())
            val password = normalizeData(binding.edtPassword.text.toString())
            if (validateInput(email, password)) {
                viewModel.login(email, password)
            }
        }
        binding.txtSignUp.setOnClickListener {
            goToSignUp()
        }
    }

    private fun dataObserver() {
        viewModel.signInLiveData.observe(
            this,
            Observer {
                when (it) {
                    is ResponseData.Loading -> {
                        showLoading(true)
                    }
                    is ResponseData.Success -> {
                        showLoading(false)
                        Toast.makeText(
                            SignInActivity@ this, "signInWithEmail:success",
                            Toast.LENGTH_LONG
                        ).show()
                        goToWelcome()
                    }
                    is ResponseData.Error -> {
                        showLoading(false)
                        Toast.makeText(SignInActivity@ this, it.message(), Toast.LENGTH_LONG).show()
                    }
                }
            }
        )
    }

    private fun goToWelcome() {
        startActivity(Intent(this, WelcomeActivity::class.java))
    }

    private fun goToSignUp() {
        startActivity(Intent(this, SignUpActivity::class.java))
    }

    private fun validateInput(email: String, password: String): Boolean {
        if (email.isBlank()) {
            Toast.makeText(this, "Please enter the email", Toast.LENGTH_SHORT).show()
            return false
        }

        if (password.isBlank()) {
            Toast.makeText(this, "Please enter the password", Toast.LENGTH_SHORT).show()
            return false
        }

        return if (isEmailValid(email)) {
            true
        } else {
            Toast.makeText(this, "Please fill correct format email", Toast.LENGTH_SHORT).show()
            false
        }
    }

    private fun normalizeData(text: String?): String {
        return if (text.isNullOrBlank()) {
            ""
        } else {
            var result = text.trim().replace("\\s+".toRegex(), " ")
            result
        }
    }

    fun isEmailValid(text: String): Boolean {
        return !TextUtils.isEmpty(text) && android.util.Patterns.EMAIL_ADDRESS.matcher(text)
            .matches()
    }
}
