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

            // Logika Sederhana untuk Login
            // Di sini Anda dapat memeriksa kredensial Anda
            // Misalnya, memeriksa apakah alamat email dan kata sandi cocok dengan data yang benar.
            if (AlamatEmail == "admin" && KataSandi == "admin") {
                // Simpan informasi login ke SharedPreferences
                val sharedPreferences = getSharedPreferences("LoginPrefs", MODE_PRIVATE)
                val editor = sharedPreferences.edit()
                editor.putBoolean("isLoggedIn", true)
                editor.apply()

                // Jika Login Berhasil, tampilkan pesan sukses
                val intent = Intent(this, HomeScreenActivity::class.java)
                startActivity(intent)
                Toast.makeText(this, "Login Berhasil", Toast.LENGTH_SHORT).show()
            } else {
                // Jika Login Gagal, tampilkan pesan kesalahan
                Toast.makeText(this, "Login Gagal", Toast.LENGTH_SHORT).show()
                Toast.makeText(this, "Username atau Password Salah", Toast.LENGTH_SHORT).show()
            }
        }
    }
}