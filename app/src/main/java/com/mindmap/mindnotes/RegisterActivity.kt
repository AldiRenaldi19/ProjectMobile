package com.mindmap.mindnotes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.mindmap.mindmaplibrary.sharedpreferences.registerdata.Profile
import com.mindmap.mindmaplibrary.sharedpreferences.registerdata.ProfilePref

class RegisterActivity : AppCompatActivity() {

    private lateinit var editTextnamaPanjang: EditText
    private lateinit var editTextnomorTelepon: EditText
    private lateinit var editTextalamatEmail: EditText
    private lateinit var editTextkataSandi: EditText
    private lateinit var daftar: Button
    private lateinit var sudahMemilikiAkun: TextView
    private lateinit var profilePref: ProfilePref

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        editTextnamaPanjang = findViewById(R.id.namaPanjang)
        editTextnomorTelepon = findViewById(R.id.nomorTelepon)
        editTextalamatEmail = findViewById(R.id.alamatEmail)
        editTextkataSandi = findViewById(R.id.kataSandi)
        daftar = findViewById(R.id.daftar)
        sudahMemilikiAkun = findViewById(R.id.sudahMemilikiAkun)

        profilePref = ProfilePref(this)

        daftar.setOnClickListener {

            // Menerapkan logika untuk mendaftarkan akun baru
            val namaPanjang = editTextnamaPanjang.text.toString()
            val nomorTelepon = editTextnomorTelepon.text.toString()
            val alamatEmail = editTextalamatEmail.text.toString()
            val kataSandi = editTextkataSandi.text.toString()

            if (validateInput(namaPanjang, nomorTelepon, alamatEmail, kataSandi)) {
                // Simpan data pendaftaran ke ProfilePref registerdata
                val profile = Profile(
                    namaPanjang = namaPanjang,
                    nomorTelepon = nomorTelepon,
                    alamatEmail = alamatEmail,
                    kataSandi = kataSandi // Enkripsi kata sandi di sini jika diperlukan
                )
                profilePref.setProfile(profile)

                // Logika menuju ke Home Screen
                // Misalnya, menggunakan Intent atau Navigation Component
                val intent = Intent(this, HomeScreenActivity::class.java)
                Toast.makeText(this, "Pendaftaran Berhasil", Toast.LENGTH_SHORT).show()
                startActivity(intent)
                finish()
            }
        }
        // Jika sudah memiliki akun, arahkan ke halaman login
        sudahMemilikiAkun.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun validateInput(
        namaPanjang: String,
        nomorTelepon: String,
        alamatEmail: String,
        kataSandi: String
    ): Boolean {
        if (namaPanjang.isEmpty() || nomorTelepon.isEmpty() || alamatEmail.isEmpty() || kataSandi.isEmpty()) {
            Toast.makeText(this, "Semua field harus diisi", Toast.LENGTH_SHORT).show()
            return false
        }
        if (kataSandi.length < 8) {
            Toast.makeText(this, "Kata sandi minimal 8 karakter", Toast.LENGTH_SHORT).show()
            return false
        }
        if (!isValidEmail(alamatEmail)) {
            // Validasi email menggunakan regex
            editTextalamatEmail.error = "Alamat email tidak valid"
            editTextalamatEmail.text.clear()
            editTextalamatEmail.requestFocus()
            return false
        }
        // Tambahkan validasi lain sesuai kebutuhan aplikasi Anda
        // Misalnya, validasi format nomor telepon, kata sandi yang kuat, dll.
        return true
        // Jika semua validasi berhasil, kembalikan true
    }

    private fun isValidEmail(email: String): Boolean {
        val emailRegex = Regex("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")
        return email.matches(emailRegex)
    }
}