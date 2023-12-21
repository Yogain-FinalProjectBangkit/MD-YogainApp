package com.example.capstone_yogain.presentation.login_register

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.capstone_yogain.R
import com.example.capstone_yogain.data.model.UserModel
import com.example.capstone_yogain.data.repo.UserRepository
import com.example.capstone_yogain.utils.showToast
import kotlinx.coroutines.launch

class UserViewModel(application: Application) : AndroidViewModel(application) {
    private val userRepository = UserRepository(application)
    private val _loginResult = MutableLiveData<Boolean>()
    val loginResult: LiveData<Boolean> get() = _loginResult

    fun insertUser(userModel: UserModel){
        viewModelScope.launch {
            userRepository.insertUser(userModel)
        }
    }


    fun loginUser(username: String, password: String) {
        viewModelScope.launch {
            try {
                val user = userRepository.loginUser(username, password)
                if (user != null) {
                    _loginResult.value = true
                } else {
                    _loginResult.value = false
                    showToast(getApplication(), R.string.error)
                }
            } finally {

            }
        }
    }

}