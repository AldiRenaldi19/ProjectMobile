package com.mindmap.mindnotes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class RegisterActivity : AppCompatActivity() {

    private lateinit var editTextnamaPanjang: EditText
    private lateinit var editTextnomorTelepon: EditText
    private lateinit var editTextalamatEmail: EditText
    private lateinit var editTextkataSandi: EditText
    private lateinit var daftar: Button
    private lateinit var sudahMemilikiAkun: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        editTextnamaPanjang = findViewById(R.id.namaPanjang)
        editTextnomorTelepon = findViewById(R.id.nomorTelepon)
        editTextalamatEmail = findViewById(R.id.alamatEmail)
        editTextkataSandi = findViewById(R.id.kataSandi)
        daftar = findViewById(R.id.daftar)
        sudahMemilikiAkun = findViewById(R.id.sudahMemilikiAkun)

        daftar.setOnClickListener {

            // Menerapkan logika untuk mendaftarkan akun baru
            val namaPanjang = editTextnamaPanjang.text.toString()
            val nomorTelepon = editTextnomorTelepon.toString()
            val alamatEmail = editTextalamatEmail.toString()
            val kataSandi = editTextkataSandi.toString()

            if (namaPanjang.isEmpty() || nomorTelepon.isEmpty() || alamatEmail.isEmpty() || kataSandi.isEmpty()) {
                Toast.makeText(this, "Mohon isi semua kolom", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (kataSandi.length < 8) {
                Toast.makeText(this, "Kata sandi minimal 8 karakter", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (!alamatEmail.contains("@") || !alamatEmail.contains(".com") || !alamatEmail.contains(".")) {
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
                val namapanjang = editTextnamaPanjang.text.toString()
                val nomortelepon = editTextnomorTelepon.text.toString()
                val alamatemail = editTextalamatEmail.text.toString()
                val katasandi = editTextkataSandi.text.toString()
                val sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)
                val editor = sharedPreferences.edit()

                // Simpan data pendaftaran ke SharedPreferences
                editor.putString("namaPanjang", namapanjang)
                editor.putString("nomorTelepon", nomortelepon)
                editor.putString("alamatEmail", alamatemail)
                editor.putString("kataSandi", katasandi)
                editor.putBoolean("isLoggedIn", true)
                editor.apply()

                // Logika menuju ke Home Screen
                val intent = Intent(this, HomeScreenActivity::class.java)
                Toast.makeText(this, "Pendaftaran Berhasil", Toast.LENGTH_SHORT).show()
                startActivity(intent)
                finish()
            }
        }

        sudahMemilikiAkun = findViewById(R.id.sudahMemilikiAkun)

        sudahMemilikiAkun.setOnClickListener {

            // Menerapkan logika untuk menavigasi ke layar login
            // Logika menuju ke Login Screen
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}