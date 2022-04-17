package com.notes.miniapp.main.common

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.notes.miniapp.R
import com.notes.miniapp.main.signin.SignInActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    @Inject
    lateinit var authentication: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler(Looper.myLooper()!!).postDelayed({
            authentication.currentUser?.let { user ->
                user.reload()
                goToWelcome()
            } ?: run {
                goToSignUp()
            }
        }, 2000)
    }

    private fun goToWelcome() {
        startActivity(Intent(this, WelcomeActivity::class.java))
        finish()
    }

    private fun goToSignUp() {
        startActivity(Intent(this, SignInActivity::class.java))
        finish()
    }
}
