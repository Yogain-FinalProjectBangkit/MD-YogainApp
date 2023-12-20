package com.example.capstone_yogain.data.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.capstone_yogain.data.model.PoseData
import com.example.capstone_yogain.data.model.PoseModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class PoseRepository private constructor() {

    private val poseAll = PoseData.pose.map {
        PoseModel(
            it.id,
            it.poseName,
            it.description,
            it.imagePose,
            it.minutes
        )
    }

    fun getPose(): Flow<List<PoseModel>> {
        return flowOf(poseAll)
    }

    fun getPoseById(poseId: String): PoseModel {
        return poseAll.first { it.id == poseId }
    }

    companion object {
        @Volatile
        private var instance: PoseRepository? = null

        fun getInstance(): PoseRepository =
            instance ?: synchronized(this) {
                instance ?: PoseRepository().also { instance = it }
            }
    }
}
