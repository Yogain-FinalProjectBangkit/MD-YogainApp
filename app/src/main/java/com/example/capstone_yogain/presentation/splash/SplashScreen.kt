package com.example.capstone_yogain.presentation.splash

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.capstone_yogain.databinding.ActivitySplashScreenBinding
import com.example.capstone_yogain.presentation.main.MainActivity
import com.example.capstone_yogain.presentation.on_boarding.OnBoardingActivity
import com.example.capstone_yogain.presentation.register.LoginActivity
import com.example.capstone_yogain.utils.ConstVal.duration
import com.google.firebase.auth.FirebaseAuth

@SuppressLint("CustomSplashScreen")
class SplashScreen : AppCompatActivity() {

    private var _binding: ActivitySplashScreenBinding? = null
    private val binding get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        Handler(Looper.getMainLooper()).postDelayed({
            checkAuthentication()
        }, duration)
    }

    private fun checkAuthentication() {
        val auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser

        if (currentUser != null) {
            // User is already authenticated, go to MainActivity
            startActivity(Intent(this, MainActivity::class.java))
        } else {
            // User is not authenticated, go to LoginActivity
            startActivity(Intent(this, OnBoardingActivity::class.java))
        }

        finish() // Close the splash screen
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
