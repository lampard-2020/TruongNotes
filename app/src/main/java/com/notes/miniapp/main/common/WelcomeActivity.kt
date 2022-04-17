package com.notes.miniapp.main.common

import android.content.Intent
import android.os.Bundle
import com.notes.miniapp.databinding.ActivityWelcomeBinding
import com.notes.miniapp.extension.viewBinding
import com.notes.miniapp.main.base.BaseActivity
import com.notes.miniapp.main.list.ListActivity

class WelcomeActivity : BaseActivity() {

    private val binding by viewBinding(ActivityWelcomeBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.butList.setOnClickListener {
            startActivity(Intent(this, ListActivity::class.java))
        }
    }
}
