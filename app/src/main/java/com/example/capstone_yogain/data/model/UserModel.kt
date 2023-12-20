package com.example.capstone_yogain.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class UserModel(
    @PrimaryKey(autoGenerate = true) var id: Long = 0,
    @ColumnInfo(name = "name")
    val name: String?,
    @ColumnInfo(name = "email")
    val email: String?,
    @ColumnInfo(name = "password")
    val password: String?,
)