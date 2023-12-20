package com.example.capstone_yogain.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.capstone_yogain.R

class NotificationWorker(val context: Context, parameters: WorkerParameters) : Worker(context, parameters) {
    override fun doWork(): Result {
        val input = inputData.getString("name").toString()
        sendNotification(context, input)
        return Result.success()
    }

    private fun sendNotification(context: Context, input: String) {
        val builder = NotificationCompat.Builder(context, "my_channel")
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle("Task Alert!")
            .setContentText("Time to do $input")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        val manager: NotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val channel = NotificationChannel("my_channel", "text", NotificationManager.IMPORTANCE_DEFAULT)
        channel.description = "This channel send task scheduling notifications"
        manager.createNotificationChannel(channel)
        manager.notify(1, builder.build())
    }
}