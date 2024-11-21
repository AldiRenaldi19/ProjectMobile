package com.example.mindnotes

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class LoginActivity : AppCompatActivity() {

    private lateinit var AlamatEmailEditText : EditText
    private lateinit var KataSandiEditText : EditText
    private lateinit var buttonMasuk : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        AlamatEmailEditText = findViewById(R.id.AlamatEmail)
        KataSandiEditText = findViewById(R.id.KataSandi)
        buttonMasuk = findViewById(R.id.buttonMasuk)

        buttonMasuk.setOnClickListener {
            val AlamatEmail = AlamatEmailEditText.text.toString()
            val KataSandi = KataSandiEditText.text.toString()

            // Logika Sederhana untuk Login
            if (AlamatEmail == "admin" && KataSandi == "password") {
                Toast.makeText(this, "Login Berhasil", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Username atau Password Salah", Toast.LENGTH_SHORT).show()
            }
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}