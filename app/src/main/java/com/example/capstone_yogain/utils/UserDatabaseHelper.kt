package com.example.capstone_yogain.utils

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.security.MessageDigest

class UserDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "user.db"
        private const val TABLE_NAME = "users"
        private const val COLUMN_ID = "id"
        private const val COLUMN_USERNAME = "username"
        private const val COLUMN_EMAIL = "email"
        private const val COLUMN_PASSWORD = "password"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery = "CREATE TABLE IF NOT EXISTS $TABLE_NAME ($COLUMN_ID INTEGER PRIMARY KEY, " +
                " $COLUMN_EMAIL TEXT,  $COLUMN_USERNAME TEXT, $COLUMN_PASSWORD TEXT)"
        db?.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun registerUser(username : String, email: String, password: String): Long {
        val contentValues = ContentValues()
        contentValues.put(COLUMN_USERNAME, username)
        contentValues.put(COLUMN_EMAIL, email)
        contentValues.put(COLUMN_PASSWORD, hashPassword(password))

        val db = this.writableDatabase
        val result = db.insert(TABLE_NAME, null, contentValues)
        db.close()

        return result
    }

    fun loginUser(email: String, password: String): Boolean {
        val hashedPassword = hashPassword(password)
        val db = this.readableDatabase
        val query = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_USERNAME = ? AND $COLUMN_PASSWORD = ?"
        val cursor: Cursor = db.rawQuery(query, arrayOf(email, hashedPassword))
        val isLoggedIn = cursor.count > 0

        cursor.close()
        db.close()

        return isLoggedIn
    }

    @SuppressLint("Range")
    fun getCurrentUser(): String? {
        // Contoh sederhana untuk mendapatkan pengguna yang sedang login
        val db = this.readableDatabase
        val query = "SELECT $COLUMN_EMAIL FROM $TABLE_NAME LIMIT 1"
        val cursor: Cursor = db.rawQuery(query, null)
        var currentUser: String? = null

        if (cursor.moveToFirst()) {
            currentUser = cursor.getString(cursor.getColumnIndex(COLUMN_USERNAME))
        }

        cursor.close()
        db.close()

        return currentUser
    }

    private fun hashPassword(password: String): String {
        val md = MessageDigest.getInstance("SHA-256")
        val digest = md.digest(password.toByteArray())
        return digest.fold("", { str, it -> str + "%02x".format(it) })
    }
}