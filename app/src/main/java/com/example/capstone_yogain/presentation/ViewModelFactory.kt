package com.example.capstone_yogain.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.capstone_yogain.data.repo.PoseRepository
import com.example.capstone_yogain.presentation.home.HomeViewModel

class ViewModelFactory(private val poseRepository: PoseRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HomeViewModel(poseRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}