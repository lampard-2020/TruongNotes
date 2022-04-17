package com.notes.miniapp.main.signup

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.notes.miniapp.databinding.ActivitySignUpBinding
import com.notes.miniapp.extension.viewBinding
import com.notes.miniapp.main.base.BaseActivity
import com.notes.miniapp.main.common.WelcomeActivity
import com.notes.miniapp.utils.ResponseData

class SignUpActivity : BaseActivity() {

    private val binding by viewBinding(ActivitySignUpBinding::inflate)
    private val viewModel: SignUpViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initView()
        dataObserver()
    }

    private fun initView() {
        binding.butLogin.setOnClickListener {
            // TODO: Should validate user input before signup
            val email = normalizeData(binding.edtAccount.text.toString())
            val password = normalizeData(binding.edtPassword.text.toString())
            val userName = normalizeData(binding.edtUserName.text.toString())
            if (validateInput(email, password)) {
                viewModel.register(email, password, userName)
            }
        }

        binding.txtSignIn.setOnClickListener {
            finish()
        }
    }

    private fun dataObserver() {
        viewModel.signUpLiveData.observe(
            this,
            Observer {
                when (it) {
                    is ResponseData.Loading -> {
                        showLoading(true)
                    }
                    is ResponseData.Success -> {
                        showLoading(false)
                        Toast.makeText(
                            SignUpActivity@ this, "signUpWithEmail:success",
                            Toast.LENGTH_LONG
                        ).show()
                        goToWelcome()
                    }
                    is ResponseData.Error -> {
                        showLoading(false)
                        Toast.makeText(SignUpActivity@ this, it.message(), Toast.LENGTH_LONG).show()
                    }
                }
            }
        )
    }

    fun goToWelcome() {
        val intent = Intent(this, WelcomeActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or
            Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
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

    fun normalizeData(text: String?): String {
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
