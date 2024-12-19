package com.mindmap.mindnotes

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class HomeScreenActivity : AppCompatActivity() {

    private lateinit var inputFileButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_screen)

        inputFileButton = findViewById(R.id.inputFileButton)
    }

    override fun onResume() {
        super.onResume()
        inputFileButton.isEnabled = true
        inputFileButton.setOnClickListener {
            val intent = Intent(this, com.mindmap.mindnotes.MainActivity::class.java)
            startActivity(intent)
        }
    }
}