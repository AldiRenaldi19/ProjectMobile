package com.mindmap.mindnotes

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.mindmap.mindnotes.logindata.Profile
import com.mindmap.mindnotes.logindata.ProfilePref

class LoginActivity : AppCompatActivity() {

    private lateinit var editTextAlamatEmail: EditText
    private lateinit var editTextKataSandi: EditText
    private lateinit var buttonbuttonMasuk: Button
    private lateinit var daftarAkunBaru: TextView
    private lateinit var profilePref: ProfilePref

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        editTextAlamatEmail = findViewById(R.id.AlamatEmail)
        editTextKataSandi = findViewById(R.id.KataSandi)
        buttonbuttonMasuk = findViewById(R.id.buttonMasuk)
        daftarAkunBaru = findViewById(R.id.daftarAkunBaru)

        profilePref = ProfilePref(this)

        buttonbuttonMasuk.setOnClickListener {
            val AlamatEmail = editTextAlamatEmail.text.toString()
            val KataSandi = editTextKataSandi.text.toString()

            if (validateInput(AlamatEmail, KataSandi)) {
                // Simpan data login ke ProfilePref logindata
                val profile = Profile(
                    AlamatEmail = AlamatEmail,
                    KataSandi = KataSandi
                )
                profilePref.setProfile(profile)

                // Logika menuju ke Home Screen
                val intent = Intent(this, HomeScreenActivity::class.java)
                Toast.makeText(this, "Login Berhasil", Toast.LENGTH_SHORT).show()
                startActivity(intent)
                finish()
            }
        }

        daftarAkunBaru.setOnClickListener {
            // Jika pengguna ingin mendaftarkan akun baru, arahkan ke halaman pendaftaran
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
    private fun validateInput(
        AlamatEmail: String,
        KataSandi: String
    ): Boolean {
        if (AlamatEmail.isEmpty() || KataSandi.isEmpty()) {
            Toast.makeText(this, "Semua field harus diisi", Toast.LENGTH_SHORT).show()
            return false
        }
        if (KataSandi.length < 8) {
            Toast.makeText(this, "Kata sandi minimal 8 karakter", Toast.LENGTH_SHORT).show()
            return false
        }
        if (!isValidEmail(AlamatEmail)) {
            // Validasi email menggunakan regex
            editTextAlamatEmail.error = "Alamat email tidak valid"
            editTextAlamatEmail.text.clear()
            editTextAlamatEmail.requestFocus()
            return false
        }
        return true
    }
    private fun isValidEmail(email: String): Boolean {
        val emailRegex = Regex("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")
        return email.matches(emailRegex)
    }
}
