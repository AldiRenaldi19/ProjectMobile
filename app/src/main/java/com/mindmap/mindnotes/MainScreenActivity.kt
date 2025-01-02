package com.mindmap.mindnotes

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.mindmap.mindnotes.logindata.ProfilePref

class MainScreenActivity : AppCompatActivity() {

    private lateinit var menujuLoginScreen: Button
    private lateinit var profilePref: ProfilePref

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_screen)

        menujuLoginScreen = findViewById(R.id.menujuLoginScreen)
        profilePref = ProfilePref(this)

        val profile = profilePref.getProfile()
        if (profile != null) {
            val intent = Intent(this, HomeScreenActivity::class.java)
            startActivity(intent)
            finish()
            return
        }

        menujuLoginScreen.setOnClickListener {
            // Intent Login Screen
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
            Toast.makeText(this, "SELAMAT DATANG", Toast.LENGTH_SHORT).show()
            return@setOnClickListener
        }
    }
}