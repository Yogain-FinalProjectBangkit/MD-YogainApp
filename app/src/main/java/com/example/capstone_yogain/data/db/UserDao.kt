package com.example.capstone_yogain.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.capstone_yogain.data.model.UserModel

@Dao
interface UserDao {
    @Insert
    suspend fun addUser(userModel: UserModel)

    @Query("select * from user order by id desc")
    fun getAllUser() : LiveData<List<UserModel>>

    @Query("SELECT * FROM user WHERE email = :email")
    fun getUserById(email: String): LiveData<List<UserModel>>

    @Query("SELECT * FROM user WHERE email = :email AND password = :password")
    suspend fun loginUser(email: String, password: String): UserModel?

    @Query("UPDATE user SET password = :newPassword WHERE email = :email ")
    suspend fun updatePassword(email: String, newPassword: String)
}