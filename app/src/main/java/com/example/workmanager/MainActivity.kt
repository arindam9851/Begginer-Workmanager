package com.example.workmanager

import android.R.attr.data
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        // For sending data to worker class
        val data =Data.Builder()
                .putString("WORK_INPUT_KEY","Hey i am sending data")
                .build()

        val request = OneTimeWorkRequest.Builder(MyWorker::class.java)
                .setInputData(data)
                .build()

        button.setOnClickListener(View.OnClickListener {
            WorkManager.getInstance().enqueue(request)
        })

        WorkManager.getInstance().getWorkInfoByIdLiveData(request.id)
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