package com.example.activities

import android.app.ActivityManager
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ActivityC : AppCompatActivity() {
    override fun onResume() {
        super.onResume()
        printActivityStack()
    }

    fun printActivityStack(tag: String = "ActivityStack") {
        val activityManager = getSystemService(ACTIVITY_SERVICE) as ActivityManager

        Log.d(tag, "=== CURRENT APP TASKS ===")

        activityManager.appTasks.forEachIndexed { taskIndex, appTask ->
            val taskInfo = appTask.taskInfo

            if (taskInfo.id == -1) return

            Log.d(tag, "AppTask #$taskIndex")
            Log.d(tag, "\tTask ID: ${taskInfo.id}")
            Log.d(tag, "\tNumber of Activities: ${taskInfo.numActivities}")
            Log.d(tag, "\tBase Activity: ${taskInfo.baseActivity?.className}")
            Log.d(tag, "\tTop Activity: ${taskInfo.topActivity?.className}")

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                Log.d(tag, "\tisRunning: ${taskInfo.isRunning}")
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        window.addFlags(WindowManager.LayoutParams.FLAG_SECURE)
        setContentView(R.layout.activity_c)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        findViewById<Button>(R.id.buttonOpenActA).setOnClickListener {
            val intent = Intent(this, ActivityA::class.java)

            startActivity(intent)
            finishAffinity()
        }
        Log.d("ActC", "onCreate")
    }

}