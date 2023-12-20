package com.example.capstone_yogain.presentation.profile

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.capstone_yogain.data.db.UserDao
import com.example.capstone_yogain.data.db.UserDatabase
import com.example.capstone_yogain.data.model.UserModel
import com.example.capstone_yogain.data.repo.UserRepository
import kotlinx.coroutines.launch

class ProfileViewModel(application: Application) : AndroidViewModel(application) {
    private val userDao: UserDao
    private val repository =  UserRepository(application)

    init {
        val database = UserDatabase.getDBUser(application)
        userDao = database.getUserDao()
    }

    fun getUserByEmail(email: String): LiveData<List<UserModel>> {
        return userDao.getUserById(email)
    }

    suspend fun updatePassword(email: String, newPassword: String) {
        repository.updatePassword(email, newPassword)
    }

}