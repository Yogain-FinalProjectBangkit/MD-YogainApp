package com.example.capstone_yogain.presentation.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.core.view.updatePadding
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.capstone_yogain.R
import com.example.capstone_yogain.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var _activityMainBinding: ActivityMainBinding;
    private val binding get() = _activityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(_activityMainBinding.root)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)


        val navHostBottomBar = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navControllerBottomBar = navHostBottomBar.navController

        binding.bottomNav.setupWithNavController(navControllerBottomBar)
        binding.bottomNav.background = null

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_nav)
        setupWithNavController(bottomNavigationView, navControllerBottomBar)

        ViewCompat.setOnApplyWindowInsetsListener(bottomNavigationView) { view, insets ->
            val systemGestureInsets = insets.getInsets(WindowInsetsCompat.Type.systemGestures())
            view.updatePadding(bottom = systemGestureInsets.bottom)
            insets
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.nav_host_fragment)) { view, insets ->
            val systemGestureInsets = insets.getInsets(WindowInsetsCompat.Type.systemGestures())
            view.updatePadding(bottom = systemGestureInsets.bottom)
            insets
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.nav_host_fragment)) { _, insets ->
            WindowInsetsControllerCompat(window, findViewById(R.id.nav_host_fragment)).apply {
                hide(WindowInsetsCompat.Type.systemBars())
                show(WindowInsetsCompat.Type.systemBars())
                var systemGestureInsets = insets.getInsets(WindowInsetsCompat.Type.systemGestures())
            }
            insets
        }

        window.decorView.setOnApplyWindowInsetsListener { _, insets ->
            val systemGestureInsets = insets.getInsets(WindowInsetsCompat.Type.systemGestures())
            bottomNavigationView.updatePadding(bottom = systemGestureInsets.bottom)
            insets
        }
    }
}