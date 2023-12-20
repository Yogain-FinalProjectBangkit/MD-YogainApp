package com.example.capstone_yogain.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.room.TypeConverter

class Converters {
    @TypeConverter
    fun fromStringUri(value: String?): Uri? {
        return value?.let { Uri.parse(it) }
    }

    @TypeConverter
    fun toStringUri(uri: Uri?): String? {
        return uri?.toString()
    }

}
