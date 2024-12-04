package com.example.mindnotes

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
class LoginActivity : AppCompatActivity() {

    private lateinit var AlamatEmailEditText : EditText
    private lateinit var KataSandiEditText : EditText
    private lateinit var buttonMasuk : Button
    private lateinit var lupaPasswordTextView: TextView
    private lateinit var daftarAkunBaruTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        AlamatEmailEditText = findViewById(R.id.AlamatEmail)
        KataSandiEditText = findViewById(R.id.KataSandi)
        buttonMasuk = findViewById(R.id.buttonMasuk)
        lupaPasswordTextView = findViewById(R.id.lupaPassword)
        daftarAkunBaruTextView = findViewById(R.id.daftarAkunBaru)

        daftarAkunBaruTextView.setOnClickListener{
            val intent = Intent (this, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }

        lupaPasswordTextView.setOnClickListener {
            val lupaPassword = lupaPasswordTextView.text.toString()
        }

        buttonMasuk.setOnClickListener {
            val AlamatEmail = AlamatEmailEditText.text.toString()
            val KataSandi = KataSandiEditText.text.toString()
            // Logika Menuju Home Screen
            val  Intent = Intent(this, HomeScreenActivity::class.java)
            startActivity(Intent)

            // Logika Sederhana untuk Login
            if (AlamatEmail == "admin" && KataSandi == "password") {
                Toast.makeText(this, "Login Berhasil", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Username atau Password Salah", Toast.LENGTH_SHORT).show()
            }
        }
    }
}