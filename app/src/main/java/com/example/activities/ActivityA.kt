package com.example.activities

import android.annotation.SuppressLint
import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.PowerManager
import android.util.Log
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.random.Random

class ActivityA : AppCompatActivity() {

    private lateinit var buttonOpenActB: Button
    private lateinit var buttonGenerateColor: Button
    private lateinit var editTextColor: EditText
    private lateinit var wakeLock : PowerManager.WakeLock
    companion object {
        private const val KEY_COLOR_TEXT = "color_text"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        window.addFlags(
                    WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON or
                    WindowManager.LayoutParams.FLAG_SECURE
        )
        setShowWhenLocked(true)
        setTurnScreenOn(true)
        setContentView(R.layout.activity_a)


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initViews()
        setupClickListeners()

        savedInstanceState?.getString(KEY_COLOR_TEXT)?.let { colorText ->
            editTextColor.setText(colorText)
        }

        Log.d("ActA", "onCreate")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(KEY_COLOR_TEXT, editTextColor.text.toString())
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        Log.d("ActA", "onNewIntent")
    }

    private fun initViews() {
        buttonOpenActB = findViewById(R.id.buttonOpenActB)
        buttonGenerateColor = findViewById(R.id.buttonGenerateColor)
        editTextColor = findViewById(R.id.editTextColor)
    }

    private fun setupClickListeners() {
        buttonOpenActB.setOnClickListener {
            val colorHex = editTextColor.text.toString()
            if ((colorHex == "") or !colorHex.matches(Regex("^#([0-9A-Fa-f]{6})$")))
                Toast.makeText(this, "Неверный цвет!", Toast.LENGTH_SHORT).show()
            else
                startActivity(ActivityB.newIntent(this, colorHex))
        }

        buttonGenerateColor.setOnClickListener {
            editTextColor.setText(String.format("#%06X", Random.nextInt(0x1000000)))
        }
    }

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

}