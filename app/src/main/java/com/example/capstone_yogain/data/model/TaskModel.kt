package com.example.capstone_yogain.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "task")
data class TaskModel(
    @PrimaryKey(autoGenerate = true) var id: Long = 0,
    val tittle: String,
    var des: String,
    var date: String,
    var time: String,
    var category: String,
    @ColumnInfo(name = "completed") var isCompleted: Boolean = false
)
