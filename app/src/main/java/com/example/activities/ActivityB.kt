package com.example.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

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
    }

    companion object {
        private const val EXTRA_BACKGROUND_COLOR = "BACKGROUND_COLOR"

        fun newIntent(context: Context, colorHex: String): Intent {
            return Intent(context, ActivityB::class.java).apply {
                putExtra(EXTRA_BACKGROUND_COLOR, colorHex)
            }
        }
    }
}