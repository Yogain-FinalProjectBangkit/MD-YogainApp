package com.example.capstone_yogain.presentation.camera

import android.graphics.Bitmap
import android.graphics.Rect
import androidx.camera.view.PreviewView
import com.example.capstone_yogain.data.data.Person
import com.example.capstone_yogain.ml.MoveNetMultiPose
import com.example.capstone_yogain.ml.PoseClassifier
import com.example.capstone_yogain.ml.PoseDetector
import com.example.capstone_yogain.ml.TrackerType
import com.example.poseestimation.VisualizationUtils


class CameraSource(
    private val previewView: PreviewView,
    private val listener: CameraSourceListener? = null
) {
    private var isTrackerEnabled = false
    private val lock = Any()
    private var framesPerSecond = 0
    private var detector: PoseDetector? = null
    private var classifier: PoseClassifier? = null
    private var frameProcessedInOneSecondInterval = 0
    companion object {
        private const val MIN_CONFIDENCE = 0.2f
    }

    fun setTracker(trackerType: TrackerType) {
        isTrackerEnabled = trackerType != TrackerType.OFF
        (this.detector as? MoveNetMultiPose)?.setTracker(trackerType)
    }

    fun setDetector(detector: PoseDetector) {
        synchronized(lock) {
            if (this.detector != null) {
                this.detector?.close()
                this.detector = null
            }
            this.detector = detector
        }
    }


    private fun visualize(persons: List<Person>, bitmap: Bitmap) {
        val outputBitmap = VisualizationUtils.drawBodyKeypoints(
            bitmap,
            persons.filter { it.score > MIN_CONFIDENCE }, isTrackerEnabled
        )

        // Ensure the previewView is properly assigned
        val previewView = this.previewView

        val viewWidth = previewView.width.toFloat()
        val viewHeight = previewView.height.toFloat()

        val canvas = previewView.lockCanvas()
        canvas?.let { surfaceCanvas ->
            val outputBitmapWidth = outputBitmap.width.toFloat()
            val outputBitmapHeight = outputBitmap.height.toFloat()

            val scaleX = viewWidth / outputBitmapWidth
            val scaleY = viewHeight / outputBitmapHeight

            val scale = if (scaleX < scaleY) scaleX else scaleY

            val scaledBitmapWidth = (outputBitmapWidth * scale).toInt()
            val scaledBitmapHeight = (outputBitmapHeight * scale).toInt()

            val left = (viewWidth - scaledBitmapWidth) / 2
            val top = (viewHeight - scaledBitmapHeight) / 2

            surfaceCanvas.drawBitmap(
                outputBitmap,
                null,
                RectF(left, top, left + scaledBitmapWidth, top + scaledBitmapHeight),
                null
            )

            previewView.unlockCanvasAndPost(surfaceCanvas)
        }
    }


    fun setClassifier(classifier: PoseClassifier?) {
        synchronized(lock) {
            if (this.classifier != null) {
                this.classifier?.close()
                this.classifier = null
            }
            this.classifier = classifier
        }
    }
    private fun processImage(bitmap: Bitmap) {
        val persons = mutableListOf<Person>()
        var classificationResult: List<Pair<String, Float>>? = null

        synchronized(lock) {
            detector?.estimatePoses(bitmap)?.let {
                persons.addAll(it)

                // if the model only returns one item, allow running the Pose classifier.
                if (persons.isNotEmpty()) {
                    classifier?.run {
                        classificationResult = classify(persons[0])
                    }
                }
            }
        }
        frameProcessedInOneSecondInterval++
        if (frameProcessedInOneSecondInterval == 1) {
            // send fps to view
            listener?.onFPSListener(framesPerSecond)
        }

        // if the model returns only one item, show that item's score.
        if (persons.isNotEmpty()) {
            listener?.onDetectedInfo(persons[0].score, classificationResult)
        }
        visualize(persons, bitmap)
    }


    interface CameraSourceListener {
        fun onFPSListener(fps: Int)

        fun onDetectedInfo(personScore: Float?, poseLabels: List<Pair<String, Float>>?)
    }
}


