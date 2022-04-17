package com.notes.miniapp.main.common

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.notes.miniapp.databinding.ActivityProfileBinding
import com.notes.miniapp.extension.viewBinding
import com.notes.miniapp.main.signin.SignInActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProfileActivity : AppCompatActivity() {
    private val binding by viewBinding(ActivityProfileBinding::inflate)

    @Inject
    lateinit var authentication: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initView()
    }

    private fun initView() {
        binding.txtBack.setOnClickListener {
            finish()
        }
        binding.butLogout.setOnClickListener {
            authentication.signOut()
            goToSignIn()
        }

        binding.txtEmail.text = "Email: ${authentication.currentUser?.email}"
    }

    fun goToSignIn() {
        val intent = Intent(this, SignInActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or
            Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }
}
