package com.poly.ui.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.poly.databinding.ActivityOpeningBinding
import com.poly.utils.Constant.USER_ID
import com.poly.utils.Constant.USER_TOKEN
import com.poly.utils.fullscreen
import com.poly.utils.getPreference

class OpeningActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOpeningBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOpeningBinding.inflate(layoutInflater)
        setContentView(binding.root)

        this@OpeningActivity.fullscreen()

        val token = getPreference(this@OpeningActivity, USER_ID)
        Looper.myLooper()?.let {
            Handler(it).postDelayed({
                if (token.isNotEmpty())
                    startActivity(Intent(applicationContext, MainActivity::class.java))
                else
                    startActivity(Intent(applicationContext, AuthActivity::class.java))
                finish()
            }, 2500)
        }
    }
}