package com.example.capstone_yogain.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.capstone_yogain.data.model.UserModel
import com.example.capstone_yogain.utils.Converters

@Database(entities = [UserModel::class], version = 1)
@TypeConverters(Converters::class)
abstract class UserDatabase : RoomDatabase() {
    abstract fun getUserDao(): UserDao

    companion object {
        private var userDatabase: UserDatabase? = null

        fun getDBUser(context: Context): UserDatabase {
            return userDatabase ?: synchronized(this) {
                val db = Room.databaseBuilder(context, UserDatabase::class.java, "user")
                    .fallbackToDestructiveMigration() // Handle version migration
                    .build()
                userDatabase = db
                db
            }
        }
    }
}
