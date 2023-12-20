package com.example.capstone_yogain.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.capstone_yogain.data.db.UserDao
import com.example.capstone_yogain.data.db.UserDatabase
import com.example.capstone_yogain.data.model.PoseModel
import com.example.capstone_yogain.data.model.UserModel
import com.example.capstone_yogain.data.repo.PoseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class HomeViewModel(
    private val repository: PoseRepository
) : ViewModel() {
    fun getPose(): Flow<List<PoseModel>> {
        return repository.getPose()
    }
}