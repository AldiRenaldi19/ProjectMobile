package com.example.mindnotes

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class HomeScreenActivity : AppCompatActivity() {

    private lateinit var inputFileButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home_screen)

        inputFileButton = findViewById(R.id.inputFileButton)

        inputFileButton.setOnClickListener {
            val intent = Intent(this, MindMapActivity::class.java)
            startActivity(intent)
        }
    }
}