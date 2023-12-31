package com.example.capstone_yogain.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.capstone_yogain.data.model.TaskModel

@Database(entities = [TaskModel::class], version = 1)
abstract class TaskDatabase : RoomDatabase() {
    abstract fun getTaskDao() : TaskDao

    companion object {
        private var taskDatabase : TaskDatabase? = null

        fun getDB(context: Context) : TaskDatabase {
            return taskDatabase ?: synchronized(this) {
                val db = Room.databaseBuilder(context, TaskDatabase::class.java, "task")
                    .build()

                taskDatabase = db
                db
            }
        }
    }
}