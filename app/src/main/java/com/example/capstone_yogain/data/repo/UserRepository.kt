package com.example.capstone_yogain.data.repo

import android.content.Context
import android.content.SharedPreferences
import com.example.capstone_yogain.data.db.UserDatabase
import com.example.capstone_yogain.data.model.UserModel

class UserRepository (context: Context){
    private val userDao = UserDatabase.getDBUser(context).getUserDao()

    suspend fun insertUser(userModel: UserModel){
        userDao.addUser(userModel)
    }

    suspend fun loginUser(username: String, password: String) {
        userDao.loginUser(username, password)
    }

    suspend fun updatePassword(email: String, newPassword: String) {
        userDao.updatePassword(email, newPassword)
    }
}