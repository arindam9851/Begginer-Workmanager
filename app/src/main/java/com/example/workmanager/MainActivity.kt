package com.example.workmanager

import android.R.attr.data
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    var name : String=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val request = OneTimeWorkRequest.Builder(MyWorker::class.java).build()

        button.setOnClickListener(View.OnClickListener {
            WorkManager.getInstance().enqueue(request)
        })

        WorkManager.getInstance().getWorkInfoByIdLiveData(request.id)
            .observe(this, Observer {
                var status = it.state.name
                textview.append(status+"\n")
            })
    }
}