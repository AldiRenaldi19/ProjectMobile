package com.mindmap.mindnotes

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.mindmap.mindnotes.logindata.ProfilePref

class HomeScreenActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView
    private lateinit var inputFileButton: Button
    private lateinit var profilePref: ProfilePref

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_screen)

        inputFileButton = findViewById(R.id.inputFileButton)
        imageView = findViewById(R.id.imageView)
        profilePref = ProfilePref(this)

        inputFileButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        imageView.setOnClickListener {
            val intent = Intent(this, UserMenuActivity::class.java)
            startActivity(intent)
        }
    }
}