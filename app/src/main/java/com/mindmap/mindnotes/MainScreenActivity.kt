package com.mindmap.mindnotes

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainScreenActivity : AppCompatActivity() {

    private lateinit var menujuLoginScreen: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_screen)

        menujuLoginScreen = findViewById(R.id.menujuLoginScreen)

        menujuLoginScreen.setOnClickListener {
            // Intent Login Screen
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
            Toast.makeText(this, "SELAMAT DATANG", Toast.LENGTH_SHORT).show()
        }
    }
}