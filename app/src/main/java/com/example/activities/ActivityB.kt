package com.example.activities

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.text.Layout
import android.util.Log
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.graphics.toColorInt

class ActivityB : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        window.addFlags(WindowManager.LayoutParams.FLAG_SECURE)
        setContentView(R.layout.activity_b)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        findViewById<Button>(R.id.buttonOpenActC).setOnClickListener {
            val intent = Intent(this, ActivityC::class.java)

            startActivity(intent)
        }
        handleIntent(intent)
    }
    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        handleIntent(intent)
    }

    companion object {
        private const val EXTRA_BACKGROUND_COLOR = "BACKGROUND_COLOR"

        fun newIntent(context: Context, colorHex: String): Intent {
            return Intent(context, ActivityB::class.java).apply {
                putExtra(EXTRA_BACKGROUND_COLOR, colorHex)
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
            }
        }
    }
    private fun handleIntent(intent: Intent) {
        val colorHex = intent.getStringExtra(ActivityB.EXTRA_BACKGROUND_COLOR) ?: "#FF0000"
        val backgroundColor = try {
            colorHex.toColorInt()
        } catch (e: Exception) {
            Color.GREEN
        }

        findViewById<ConstraintLayout>(R.id.main).setBackgroundColor(backgroundColor)
        findViewById<TextView>(R.id.textViewColor).text = if (!colorHex.isEmpty()) colorHex else "Green"
        Log.d("ActB", "Handling intent")
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