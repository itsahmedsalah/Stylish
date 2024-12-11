package com.example.stylish.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.stylish.databinding.ActivitySplashBinding
import com.example.stylish.ui.homepage.MainActivity
import com.example.stylish.ui.splash.onbording.OnBordingActivity
import com.example.stylish.utils.MySharedPreference


class SplashScreen : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Handler(Looper.getMainLooper()).postDelayed({
            try {
                if (MySharedPreference.getUserID() == null) {
                    val intent = Intent(this@SplashScreen, OnBordingActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    val intent = Intent(this@SplashScreen, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            } catch (e: Exception) {
                println(e.message)
                val intent = Intent(this@SplashScreen, OnBordingActivity::class.java)
                startActivity(intent)
                finish()
            }


        }, 3000)

    }

}