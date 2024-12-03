package com.example.mindnotes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class RegisterActivity : AppCompatActivity() {

    private lateinit var namaPanjang: EditText
    private lateinit var nomorTelepon: EditText
    private lateinit var alamatEmail: EditText
    private lateinit var kataSandi: EditText
    private lateinit var daftar: Button
    private lateinit var sudahMemilikiAkun: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        namaPanjang = findViewById(R.id.namaPanjang)
        nomorTelepon = findViewById(R.id.nomorTelepon)
        alamatEmail = findViewById(R.id.alamatEmail)
        kataSandi = findViewById(R.id.kataSandi)
        daftar = findViewById(R.id.daftar)
        sudahMemilikiAkun = findViewById(R.id.sudahMemilikiAkun)

        daftar.setOnClickListener {
            // Menerapkan logika untuk mendaftarkan akun baru
        }

        sudahMemilikiAkun.setOnClickListener {
            // Menerapkan logika untuk menavigasi ke layar login
        }
    }
}