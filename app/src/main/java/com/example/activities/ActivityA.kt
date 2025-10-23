package com.example.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.random.Random

class ActivityA : AppCompatActivity() {

    private lateinit var buttonOpenActB: Button
    private lateinit var buttonGenerateColor: Button
    private lateinit var editTextColor: EditText

    companion object {
        private const val KEY_COLOR_TEXT = "color_text"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
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

            startActivity(ActivityB.newIntent(this, editTextColor.text.toString()))
        }

        buttonGenerateColor.setOnClickListener {
            editTextColor.setText(String.format("#%06X", Random.nextInt(0x1000000)))
        }
    }
}