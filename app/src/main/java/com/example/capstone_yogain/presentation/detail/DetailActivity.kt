package com.example.capstone_yogain.presentation.detail

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.capstone_yogain.databinding.ActivityDetailBinding
import com.example.capstone_yogain.presentation.camera.CameraActivity

class DetailActivity : AppCompatActivity() {
    private var _binding : ActivityDetailBinding?= null
    private val binding get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        // Retrieve data from the intent
        val poseName = intent.getStringExtra("POSE_NAME")
        val poseDescription = intent.getStringExtra("POSE_DESCRIPTION")
        val poseImageResourceId = intent.getIntExtra("POSE_IMAGE", 0) // Default value is 0

        // Set data to the binding
        binding?.tvGraphics?.text = poseName
        binding?.description?.text = poseDescription
        binding?.ivDetailImg?.setImageResource(poseImageResourceId)
        binding?.btnCamera?.setOnClickListener {
            val intent = Intent(this, CameraActivity::class.java)
            startActivity(intent)
        }
    }
}