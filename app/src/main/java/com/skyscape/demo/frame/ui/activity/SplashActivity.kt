package com.skyscape.demo.frame.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.skyscape.demo.frame.R

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        window.setBackgroundDrawable(null)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
    }
}