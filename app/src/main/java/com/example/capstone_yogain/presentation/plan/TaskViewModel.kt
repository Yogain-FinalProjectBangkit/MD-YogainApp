package com.example.capstone_yogain.presentation.plan

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.capstone_yogain.data.model.TaskModel
import com.example.capstone_yogain.data.repo.TaskRepository
import kotlinx.coroutines.launch

class TaskViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = TaskRepository(application)

    fun insertTask(taskModel: TaskModel){
        viewModelScope.launch {
            repository.insertTask(taskModel)
        }
    }

    fun fetchAllTasks() : LiveData<List<TaskModel>> {
        return repository.getAllTasks()
    }

    fun updateTask(taskModel: TaskModel){
        taskModel.isCompleted = !taskModel.isCompleted

        viewModelScope.launch {
            repository.updateTask(taskModel)
        }
    }

    fun deleteTask(taskModel: TaskModel){
        viewModelScope.launch {
            repository.deleteTask(taskModel)
        }
    }
}