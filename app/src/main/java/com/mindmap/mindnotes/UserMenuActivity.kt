package com.mindmap.mindnotes

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.mindmap.mindnotes.logindata.ProfilePref
import com.mindmap.mindnotes.logindata.ProfilePref.Companion.SP_NAME_LOGIN

class UserMenuActivity : AppCompatActivity() {

    private lateinit var profilePref: ProfilePref
    private lateinit var profilePicture: ImageView
    private lateinit var welomeText: TextView
    private lateinit var logoutButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_menu)

        profilePref = ProfilePref(this)
        profilePicture = findViewById(R.id.profilepicture)
        welomeText = findViewById(R.id.welcome_text)
        logoutButton = findViewById(R.id.logout_button)


        val profile = profilePref.getProfile()
        if (profile != null) {
            welomeText.text = "Selamat datang, ${profile.AlamatEmail}"
        }
        logoutButton.setOnClickListener {
            profilePref.clearProfile()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
    fun logout(view: View) {
        val sharedPreferences = getSharedPreferences(SP_NAME_LOGIN, MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()

        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
    }