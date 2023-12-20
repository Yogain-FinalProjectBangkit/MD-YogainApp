package com.example.capstone_yogain.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PoseModel(
    val id: String,
    val poseName: String,
    val description: String,
    val imagePose: Int,
    val minutes : String
) : Parcelable