package com.example.mindnotes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class RegisterActivity : AppCompatActivity() {

    private lateinit var namaPanjangEditText: EditText
    private lateinit var nomorTeleponEditText: EditText
    private lateinit var alamatEmailEditText: EditText
    private lateinit var kataSandiEditText: EditText
    private lateinit var daftar: Button
    private lateinit var sudahMemilikiAkun: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        namaPanjangEditText = findViewById(R.id.namaPanjang)
        nomorTeleponEditText = findViewById(R.id.nomorTelepon)
        alamatEmailEditText = findViewById(R.id.alamatEmail)
        kataSandiEditText = findViewById(R.id.kataSandi)
        daftar = findViewById(R.id.daftar)
        sudahMemilikiAkun = findViewById(R.id.sudahMemilikiAkun)

        daftar.setOnClickListener {
            // Menerapkan logika untuk mendaftarkan akun baru
            val namaPanjang = namaPanjangEditText.text.toString()
            val nomorTelepon = nomorTeleponEditText.toString()
            val alamatEmail = alamatEmailEditText.toString()
            val kataSandi = kataSandiEditText.toString()
            val daftar = daftar.toString()
            // Logika menuju ke Home Screen
            val Intent = Intent(this, HomeScreenActivity::class.java)
            startActivity(Intent)
            finish()
        }

        sudahMemilikiAkun = findViewById(R.id.sudahMemilikiAkun)

        sudahMemilikiAkun.setOnClickListener {
            // Menerapkan logika untuk menavigasi ke layar login
            val sudahMemilikiAkun = sudahMemilikiAkun.toString()
            // Logika menuju ke Login Screen
            val Intent = Intent(this, LoginActivity::class.java)
            startActivity(Intent)
            finish()
        }
    }
}