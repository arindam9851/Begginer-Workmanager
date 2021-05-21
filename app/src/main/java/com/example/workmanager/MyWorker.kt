package com.example.workmanager

import android.R
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters


class MyWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {
    override fun doWork(): Result {
        // getting data from mainActivity
        val data= inputData
        var desc =data.getString("WORK_INPUT_KEY")
        displayNotification("I am your work",desc!!)

        return Result.success(createOutputData())
    }

    private fun createOutputData(): Data {
        // Sending data to mainActivity
        val outputData= Data.Builder()
                .putString("WORK_OUTPUT_KEY","Hey i am sending output data from Worker class")
                .build()

        return outputData

    }


    private fun displayNotification(task: String, desc: String) {
        val manager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "simple",
                "simple",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            manager.createNotificationChannel(channel)
        }
        val builder: NotificationCompat.Builder =
            NotificationCompat.Builder(applicationContext, "simple")
                .setContentTitle(task)
                .setContentText(desc)
                .setSmallIcon(R.mipmap.sym_def_app_icon)
        manager.notify(1, builder.build())
    }
}