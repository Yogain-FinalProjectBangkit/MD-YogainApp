package com.example.capstone_yogain.ml

import android.graphics.Bitmap
import com.example.capstone_yogain.data.data.Person

interface PoseDetector : AutoCloseable {

    fun estimatePoses(bitmap: Bitmap): List<Person>

    fun lastInferenceTimeNanos(): Long
}
