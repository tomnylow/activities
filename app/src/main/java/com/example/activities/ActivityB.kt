package com.example.activities

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Layout
import android.util.Log
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
}