package com.example.capstone_yogain.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.capstone_yogain.data.model.TaskModel

@Dao
interface TaskDao {
    @Insert
    suspend fun addTask(taskModel: TaskModel)

    @Update
    suspend fun updateTask(taskModel: TaskModel)

    @Delete
    suspend fun deleteTask(taskModel: TaskModel)

    @Query("select * from task order by id desc")
    fun getAllTask() : LiveData<List<TaskModel>>
}