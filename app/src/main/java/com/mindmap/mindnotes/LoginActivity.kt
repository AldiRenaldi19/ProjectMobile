package com.mindmap.mindnotes

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.mindmap.mindnotes.sharedpreferences.logindata.Profile

class LoginActivity : AppCompatActivity() {

    private lateinit var editTextAlamatEmail: EditText
    private lateinit var editTextKataSandi: EditText
    private lateinit var buttonbuttonMasuk: Button
    private lateinit var daftarAkunBaru: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        editTextAlamatEmail = findViewById(R.id.AlamatEmail)
        editTextKataSandi = findViewById(R.id.KataSandi)
        buttonbuttonMasuk = findViewById(R.id.buttonMasuk)
        daftarAkunBaru = findViewById(R.id.daftarAkunBaru)

        buttonbuttonMasuk.setOnClickListener {
            val alamatEmail = editTextAlamatEmail.text.toString()
            val kataSandi = editTextKataSandi.text.toString()
            if (alamatEmail.isEmpty() || kataSandi.isEmpty()) {
                Toast.makeText(this, "Mohon isi semua kolom", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            } else if (kataSandi.length < 8) {
                Toast.makeText(this, "Kata sandi minimal 8 karakter", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            } else {
                Toast.makeText(this, "Login Berhasil Selamat Datang", Toast.LENGTH_SHORT).show()
            }
            val profile = Profile(alamatEmail, kataSandi)
            val sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)
            val editor = sharedPreferences.edit()

            editor.putString("alamatEmail", profile.alamatEmail)
            editor.putString("kataSandi", profile.kataSandi)
            editor.putBoolean("isLoggedIn", true)
            editor.apply()

            val intent = Intent(this, HomeScreenActivity::class.java)
            startActivity(intent)
            finish()
        }

        daftarAkunBaru.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
