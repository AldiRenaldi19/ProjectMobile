package com.example.mindnotes

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var menujuLoginScreen: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        menujuLoginScreen = findViewById(R.id.menujuLoginScreen)

        menujuLoginScreen.setOnClickListener {
            // Intent Menuju Login Screen
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}