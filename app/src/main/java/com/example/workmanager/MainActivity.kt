package com.example.workmanager

import android.R.attr.data
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.work.*
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        // For sending data to worker class
        val data =Data.Builder()
                .putString("WORK_INPUT_KEY","Hey i am sending data")
                .build()

        // When device is on charging and connected to network then work is execute
        val constrains=Constraints.Builder()
                .setRequiresCharging(true)
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()

        // One time work request
        val oneTimeRequest = OneTimeWorkRequest.Builder(MyWorker::class.java)
                .setInputData(data)
                .setConstraints(constrains)
                .build()

        // Periodic work request
       /* val periodicWorkRequest=PeriodicWorkRequest.Builder(MyWorker::class.java,20,TimeUnit.MINUTES)
                .build()*/


        button.setOnClickListener(View.OnClickListener {
            WorkManager.getInstance().enqueue(oneTimeRequest)
        })
        /*button.setOnClickListener(View.OnClickListener {
            WorkManager.getInstance().enqueue(periodicWorkRequest)
        })*/

        WorkManager.getInstance().getWorkInfoByIdLiveData(oneTimeRequest.id)
            .observe(this, Observer {
                if(it!=null){
                    if(it.state.isFinished){
                       // receiving data from worker class
                        var data=it.outputData
                        var stringData= data.getString("WORK_OUTPUT_KEY")
                        textview.append(stringData)

                    }
                }

                var status = it.state.name
                textview.append(status+"\n")
            })
    }
}