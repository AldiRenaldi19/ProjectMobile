package com.mindmap.mindnotes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

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

            if (namaPanjang.isEmpty() || nomorTelepon.isEmpty() || alamatEmail.isEmpty() || kataSandi.isEmpty()) {
                Toast.makeText(this, "Mohon isi semua kolom", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (kataSandi.length < 8) {
                Toast.makeText(this, "Kata sandi minimal 8 karakter", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (!alamatEmail.contains("@")) {
                Toast.makeText(this, "Alamat email tidak valid", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (!nomorTelepon.matches(Regex("[0-9]+"))) {
                Toast.makeText(this, "Nomor telepon hanya boleh berisi angka", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }
            if (nomorTelepon.length != 11) {
                Toast.makeText(this, "Nomor telepon harus 11 digit", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (namaPanjang.length < 3) {
                Toast.makeText(this, "Nama panjang minimal 3 karakter", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            } else {
                val namaPanjang = namaPanjangEditText.text.toString()
                val nomorTelepon = nomorTeleponEditText.text.toString()
                val alamatEmail = alamatEmailEditText.text.toString()
                val kataSandi = kataSandiEditText.text.toString()
                val daftar = daftar.toString()
                val sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)
                val editor = sharedPreferences.edit()

                // Simpan data pendaftaran ke SharedPreferences
                editor.putString("namaPanjang", namaPanjang)
                editor.putString("nomorTelepon", nomorTelepon)
                editor.putString("alamatEmail", alamatEmail)
                editor.putString("kataSandi", kataSandi)
                editor.apply()

                // Logika menuju ke Home Screen
                val Intent = Intent(this, HomeScreenActivity::class.java)
                Toast.makeText(this, "Pendaftaran Berhasil", Toast.LENGTH_SHORT).show()
                startActivity(Intent)
                finish()
            }
        }

        sudahMemilikiAkun = findViewById(R.id.sudahMemilikiAkun)

        sudahMemilikiAkun.setOnClickListener {

            // Menerapkan logika untuk menavigasi ke layar login
            val sudahMemilikiAkun = sudahMemilikiAkun.toString()
            val daftar = daftar.toString()

            // Logika menuju ke Login Screen
            val Intent = Intent(this, LoginActivity::class.java)
            startActivity(Intent)
            finish()
        }
    }
}